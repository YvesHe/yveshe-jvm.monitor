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

import java.io.Serializable;

public class JVMInfo implements Serializable {
    private static final long serialVersionUID = 3786538204509053073L;

    // heap
    private long heapInitSize;
    private long heapMaxSize;
    private long heapUsageSize;
    private long heapCommitSize;
    private double heapUsageRate;

    // thread
    private long threadTotalCount;
    private long threadCount;

    // GC
    private double gcTotalTime;
    private long gcTotalCount;
    private double gcAverageTime;

    // CPU
    private double cpuUsageRate;

    public long getHeapInitSize() {
        return heapInitSize;
    }

    public void setHeapInitSize(long heapInitSize) {
        this.heapInitSize = heapInitSize;
    }

    public long getHeapMaxSize() {
        return heapMaxSize;
    }

    public void setHeapMaxSize(long heapMaxSize) {
        this.heapMaxSize = heapMaxSize;
    }

    public long getHeapUsageSize() {
        return heapUsageSize;
    }

    public void setHeapUsageSize(long heapUsageSize) {
        this.heapUsageSize = heapUsageSize;
    }

    public long getHeapCommitSize() {
        return heapCommitSize;
    }

    public void setHeapCommitSize(long heapCommitSize) {
        this.heapCommitSize = heapCommitSize;
    }

    public double getHeapUsageRate() {
        return heapUsageRate;
    }

    public void setHeapUsageRate(double heapUsageRate) {
        this.heapUsageRate = heapUsageRate;
    }

    public long getThreadTotalCount() {
        return threadTotalCount;
    }

    public void setThreadTotalCount(long threadTotalCount) {
        this.threadTotalCount = threadTotalCount;
    }

    public long getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(long threadCount) {
        this.threadCount = threadCount;
    }

    public double getGcTotalTime() {
        return gcTotalTime;
    }

    public void setGcTotalTime(double gcTotalTime) {
        this.gcTotalTime = gcTotalTime;
    }

    public long getGcTotalCount() {
        return gcTotalCount;
    }

    public void setGcTotalCount(long gcTotalCount) {
        this.gcTotalCount = gcTotalCount;
    }

    public double getGcAverageTime() {
        return gcAverageTime;
    }

    public void setGcAverageTime(double gcAverageTime) {
        this.gcAverageTime = gcAverageTime;
    }

    public double getCpuUsageRate() {
        return cpuUsageRate;
    }

    public void setCpuUsageRate(double cpuUsageRate) {
        this.cpuUsageRate = cpuUsageRate;
    }

}
