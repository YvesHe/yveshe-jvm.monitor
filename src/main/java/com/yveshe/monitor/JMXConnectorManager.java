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

import java.io.IOException;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.yveshe.exception.MonitorException;
import com.yveshe.log.InfraLogger;

public class JMXConnectorManager {
    private final InfraLogger logger = new InfraLogger(JMXConnectorManager.class);

    private JMXConnector jmxConnector;

    public JMXConnectorManager(String host, int port) throws MonitorException {
        String url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
        try {
            JMXServiceURL serviceURL = new JMXServiceURL(url);
            jmxConnector = JMXConnectorFactory.connect(serviceURL, null);
            logger.info("jmx connector connection successful");
        } catch (IOException e) {
            logger.error("error in new connector", e);
            throw new MonitorException(e.getMessage());
        }
    }

    public MBeanServerConnection getMBeanServerConnection() throws MonitorException {
        if (jmxConnector == null) {
            throw new MonitorException("connector is closed");
        }

        try {
            return jmxConnector.getMBeanServerConnection();
        } catch (IOException e) {
            logger.error("error in get MBeanServerConnection", e);
            throw new MonitorException(e.getMessage());
        }
    }

    public void closeConnector() throws MonitorException {
        if (jmxConnector == null) {
            logger.info("connector already closed");
            return;
        }

        try {
            jmxConnector.close();
            jmxConnector = null;
        } catch (IOException e) {
            logger.error("error in closed connector", e);
            throw new MonitorException(e.getMessage());
        }
    }

}
