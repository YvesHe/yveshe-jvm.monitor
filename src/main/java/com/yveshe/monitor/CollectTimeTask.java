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
package com.yveshe.monitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.yveshe.MonitorConstant;
import com.yveshe.exception.MonitorException;
import com.yveshe.log.InfraLogger;
import com.yveshe.util.JsonUtil;

public class CollectTimeTask extends TimerTask {
    private static InfraLogger logger = new InfraLogger(CollectTimeTask.class);

    private static final String FIRST_LINE_DATA = MonitorConstant.JVM_FIRST_LINE;
    private static final String FILE_NAME = MonitorConstant.JVM_CSV;
    private File outFile;
    private final JMXClient client;

    public CollectTimeTask(JMXClient client, File outDir) {
        this.client = client;
        try {
            init(outDir);
        } catch (IOException e) {
            logger.error("erro in init CollectTimeTask", e);
            throw new RuntimeException("init CollectTimeTask failed, msg is: " + e.getMessage());
        }
    }

    private void init(File outDir) throws IOException {
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        logger.info("out-dir absolute path is " + outDir.getAbsolutePath());
        if (!outDir.isDirectory()) {
            logger.warn("out-dir is not directory");
        }
        outFile = new File(outDir, FILE_NAME);
        if (outFile.exists()) {
            outFile.delete();
            outFile.createNewFile();
        }

        BufferedWriter writer = Files.newBufferedWriter(outFile.toPath(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        writer.append(FIRST_LINE_DATA);
        writer.flush();
        writer.close();
    }

    @Override
    public void run() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTimeStr = format.format(new Date());
        logger.debug("run collect task time is %s", currentTimeStr);

        // 获取信息
        JVMInfo info;
        try {
            info = client.collectJVMInfo();
            logger.info("jvm-info is %s", JsonUtil.toJson(info, true));
            String jvmLineData = currentTimeStr + "," + info.getHeapInitSize() + "," + info.getHeapMaxSize() + ","
                + info.getHeapUsageSize() + "," + info.getHeapCommitSize() + "," + info.getHeapUsageRate() + ","
                + info.getThreadTotalCount() + "," + info.getThreadCount() + "," + info.getGcTotalTime() + ","
                + info.getGcTotalCount() + "," + info.getGcAverageTime() + "," + info.getCpuUsageRate();
            writeLineData(jvmLineData);
        } catch (MonitorException | IOException e) {
            logger.error("error in collect-time-task", e);
        }

    }

    private void writeLineData(String jvmLineData) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(outFile.toPath(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            writer.append(jvmLineData);
            writer.flush();
            writer.close();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
