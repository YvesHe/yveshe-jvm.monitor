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

public abstract class MonitorConstant {
    private MonitorConstant() {
    }

    public static final String JVM_FIRST_LINE = "Time,HeapInitSize(M),HeapMaxSize(M),HeapUsageSize(M),HeapCommitSize(M),ThreadUsageRate,ThreadTotalCount,ThreadActiveCount,Full-GC TotalTime(S),Full-GC TotalCount,Full-GC AverageTime,CPU UsageRate";
    public static final String JVM_CSV = "jvm.csv";

    public static final String LOCALHOST = "localhost";
    public static final int JMX_DEFAULT_PORT = 9147;
}
