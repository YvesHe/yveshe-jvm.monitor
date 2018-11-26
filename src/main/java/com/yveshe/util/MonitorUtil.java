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
package com.yveshe.util;

import org.apache.commons.lang3.StringUtils;

public class MonitorUtil {

    public static boolean isIp(String str) {
        boolean result = false;
        if (StringUtils.isNotBlank(str)) {
            String[] split = str.split(".");
            if (split.length == 4) {
                for (String ip : split) {
                    if (!ip.matches("[0-9]*")) {
                        break;
                    }
                    if (ip.length() > 1 && !ip.startsWith("0")) {
                        break;
                    }
                    int parseInt = Integer.parseInt(ip);
                    if (parseInt < 0 || parseInt > 255) {
                        break;
                    }
                }
                result = true;
            }
        }
        return result;
    }

    public static boolean isNumberic(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static double round2(double number) {
        return new Double(Math.round(number * 100)) / 100;
    }

}
