/**
*
* Copyright:   Copyright (c)2016
* Company:     YvesHe
* @version:    1.0
* Create at:   2018年11月26日
* Description:
*
* Author       YvesHe
*/
package com.yveshe;

import java.io.File;
import java.util.Timer;

import com.yveshe.exception.MonitorException;
import com.yveshe.log.InfraLogger;
import com.yveshe.monitor.CollectTimeTask;
import com.yveshe.monitor.JMXClient;
import com.yveshe.util.MonitorUtil;

public class MonitorEntrance {
    private static final InfraLogger logger = new InfraLogger(MonitorEntrance.class);

    /**
     * Parameter: <br>
     * IP:Port + Time(s/m) + Dir
     *
     * @param args
     */
    public static void main(String[] args) {

        // check parameters
        boolean success = checkArguments(args);
        if (!success) {
            return;
        }

        // covert time
        String timeStr = args[1].toLowerCase();
        char unit = timeStr.charAt(timeStr.length() - 1);
        long timeCount = Integer.parseInt(timeStr.substring(0, timeStr.length() - 1)) * 1000;
        if (unit == 'm') {
            timeCount = timeCount * 60 * 1000;
        }

        // collect data
        try {
            String[] ipPort = args[0].toLowerCase().split(":");
            String ip = ipPort[0];
            int port = Integer.parseInt(ipPort[1]);
            JMXClient client = new JMXClient(ip, port);
            client.init();

            String dir = args[2];
            long delay = (10 - (System.currentTimeMillis() / 1000 % 10)) * 1000;
            long period = timeCount;
            logger.info("delay time is %sms, period time is %sms ", delay, period);
            CollectTimeTask task = new CollectTimeTask(client, new File(dir));
            new Timer().schedule(task, delay, period);
        } catch (MonitorException e) {
            echoError("system run failed, please see the system log!");
            logger.error("error in main", e);
        }

    }

    private static boolean checkArguments(String[] args) {
        if (args == null || args.length < 3) {
            echoNoticeMessage();
            return false;
        }

        String ipPortStr = args[0].toLowerCase();
        String[] ipPort = ipPortStr.split(":");
        if (ipPort.length < 2) {
            echoNoticeMessage();
            return false;
        }

        // ip
        if (!ipPort[0].equals("localhost") && !MonitorUtil.isIp(ipPort[0])) {
            echoNoticeMessage();
            return false;
        }

        // port
        if (!MonitorUtil.isNumberic(ipPort[1])) {
            echoNoticeMessage();
            return false;
        }

        // time
        String time = args[1].toLowerCase();
        char lastChar = time.charAt(time.length() - 1);
        if (lastChar != 's' && lastChar != 'm') {
            echoNoticeMessage();
            return false;
        }
        String timeNum = time.substring(0, time.length() - 1);
        if (!MonitorUtil.isNumberic(timeNum)) {
            echoNoticeMessage();
            return false;
        }
        if (lastChar != 's') {
            if (Integer.parseInt(timeNum) % 10 != 0) {
                echoError("when units are seconds, time must be an integer multiple of 10");
                return false;
            }
        }

        // directory
        String dir = args[2];
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.exists()) {
            echoError("dir is not exist!");
            return false;
        }
        if (!file.isDirectory()) {
            echoError("dir is not a directory");
            return false;
        }
        return true;
    }

    private static void echoNoticeMessage() {
        echoError("Please check the runtime parameters, please enter the following parameters as runing parameters: <ip>:<port> <time> <dir>");
        echoError("\tExample: localhost:9008 30s sample");
    }

    private static void echoError(String msg) {
        System.err.println(msg);
    }
}
