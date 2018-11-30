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
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import javax.management.MBeanServerConnection;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import com.yveshe.MonitorConstant;
import com.yveshe.exception.MonitorException;
import com.yveshe.log.InfraLogger;
import com.yveshe.util.MonitorUtil;

/**
 * Usage:<br>
 * instance -> initial -> do...
 *
 * @author YvesHe
 *
 */
public class JMXClient {
    private static InfraLogger logger = new InfraLogger(JMXClient.class);
    private static final long MB = 1024 * 1024;
    private String host = MonitorConstant.LOCALHOST;
    private int port = MonitorConstant.JMX_DEFAULT_PORT;

    private JMXConnectorManager connectorManger;
    private MBeanServerConnection mBeanServerConnection;

    public JMXClient() {
    }

    public JMXClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void init() throws MonitorException {
        connectorManger = new JMXConnectorManager(host, port);
        mBeanServerConnection = connectorManger.getMBeanServerConnection();
    }

    public JVMInfo collectMemoryInfo() throws MonitorException {
        try {
            MemoryMXBean memoryBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);

            MemoryUsage heap = memoryBean.getHeapMemoryUsage();
            JVMInfo info = new JVMInfo();
            info.setHeapCommitSize(heap.getCommitted() / MB);
            info.setHeapInitSize(heap.getInit() / MB);
            info.setHeapMaxSize(heap.getMax() / MB);
            info.setHeapUsageSize(heap.getUsed() / MB);
            double rate = new Long(heap.getUsed()).doubleValue() / new Long(heap.getCommitted()).doubleValue();
            info.setHeapUsageRate(rate);
            return info;
        } catch (IOException e) {
            logger.error("error in collect memory info", e);
            throw new MonitorException("error in collect memory info");
        }
    }

    public JVMInfo collectCPUInfo() throws MonitorException {
        try {
            OperatingSystemMXBean systemBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, com.sun.management.OperatingSystemMXBean.class);

            JVMInfo info = new JVMInfo();
            info.setCpuUsageRate(systemBean.getProcessCpuLoad());
            return info;
        } catch (IOException e) {
            logger.error("error in collect cpu info", e);
            throw new MonitorException("error in collect cpu info");
        }
    }

    public JVMInfo collectThreadInfo() throws MonitorException {
        try {
            ThreadMXBean threadBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);

            JVMInfo info = new JVMInfo();
            info.setThreadCount(threadBean.getTotalStartedThreadCount());
            info.setThreadTotalCount(threadBean.getTotalStartedThreadCount());
            return info;
        } catch (IOException e) {
            logger.error("error in collect thread info", e);
            throw new MonitorException("error in collect thread info");
        }
    }

    public JVMInfo collectGarbageCollectorInfo() throws MonitorException {
        try {
            String collectorName = "PS MarkSweep";
            GarbageCollectorMXBean gc = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",name=" + collectorName, GarbageCollectorMXBean.class);

            JVMInfo info = new JVMInfo();
            info.setGcTotalCount(gc.getCollectionCount());
            info.setGcTotalTime(MonitorUtil.round2(new Double(gc.getCollectionTime())));
            double averageTime = new Double(gc.getCollectionCount()) / new Double(gc.getCollectionTime());
            info.setGcAverageTime(MonitorUtil.round2(averageTime));
            return info;
        } catch (IOException e) {
            logger.error("error in collect gc info", e);
            throw new MonitorException("error in collect gc info");
        }
    }

    /**
     * Collect All Info
     *
     * @return
     * @throws MonitorException
     */
    public JVMInfo collectJVMInfo() throws MonitorException {
        JVMInfo result = new JVMInfo();

        JVMInfo heapInfo = this.collectMemoryInfo();
        result.setHeapCommitSize(heapInfo.getHeapCommitSize());
        result.setHeapInitSize(heapInfo.getHeapInitSize());
        result.setHeapMaxSize(heapInfo.getHeapMaxSize());
        result.setHeapUsageSize(heapInfo.getHeapUsageSize());
        result.setHeapUsageRate(heapInfo.getHeapUsageRate());

        JVMInfo threadInfo = this.collectThreadInfo();
        result.setThreadCount(threadInfo.getThreadCount());
        result.setThreadTotalCount(threadInfo.getThreadTotalCount());

        JVMInfo gcInfo = this.collectGarbageCollectorInfo();
        result.setGcTotalCount(gcInfo.getGcTotalCount());
        result.setGcTotalTime(gcInfo.getGcTotalTime());
        result.setGcAverageTime(gcInfo.getGcAverageTime());

        return result;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
