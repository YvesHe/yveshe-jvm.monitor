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
package com.yveshe.log;

import org.apache.log4j.Logger;

/**
 * 日志类: debug->info->warn->error->fatal
 *
 * @author YvesHe
 *
 */
public class InfraLogger {

    private final Logger logger;

    public InfraLogger(Class<?> clazz) {
        logger = Logger.getLogger(clazz);

    }

    public void debug(CharSequence msg, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(formatMSG(msg, args));
        }
    }

    public void info(CharSequence msg, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(formatMSG(msg, args));
        }
    }

    public void warn(CharSequence msg, Object... args) {
        logger.warn(formatMSG(msg, args));
    }

    public void warn(CharSequence msg, Throwable t, Object... args) {
        logger.warn(formatMSG(msg, args), t);
    }

    public void error(CharSequence msg, Object... args) {
        logger.error(formatMSG(msg, args));
    }

    public void error(CharSequence msg, Throwable t, Object... args) {
        logger.error(formatMSG(msg, args), t);
    }

    public void fatal(CharSequence msg, Object... args) {
        logger.fatal(formatMSG(msg, args));
    }

    public void fatal(CharSequence msg, Throwable t, Object... args) {
        logger.fatal(formatMSG(msg, args), t);
    }

    private String formatMSG(CharSequence msg, Object... args) {
        return String.format(msg.toString(), args);
    }

    public boolean isDebug() {
        return logger.isDebugEnabled();
    }

}