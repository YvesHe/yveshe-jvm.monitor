# 工具用途
通过一个Java的进程PID(或JMX),获取指定JVM的状态信息,并输出到文件

# 采集项目
内存(堆初始大小/堆最大值/提交内存),线程(线程使用率/线程总数/活跃线程数),GC信息(Full-GC总时间/Full-GC总次数/Full-GC平均时间),CPU(暂用率)

# 采集频率
可配置:  10S,20S,30S,1+M,一分钟以上以分钟为单位

# 结果输出
可以指定一个结果目录,具体对应结果文件:jvm.csv

# JVM参数配置

```
-Dcom.sun.management.jmxremote.port=9008 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
```
**注意:** 
参数`com.sun.management.jmxremote.port`的值为远程连接端口,只要保证端口不会被其他程序占用,不会引起冲突既可.

以Tomcat中间件为例,在`catalina.sh/catalina.bat`中JAVA_OPTS选项中添加如下配置配置
```
-Dcom.sun.management.jmxremote.port=9008 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
```

# 工具使用说明

注意 `<jvm-monitor>`指代的是jvm-monitor的工作目录
这里以Linux系统环境为例子,首先通过jvm-monitor源码编译成可执行jar,然后确保中间件产品中正确配置了`JVM参数配置`中的参数,最后按照以下步骤使用即可:

- 1.添加可执行权限
`chmod +x <jvm-monitor>/start.sh`
- 2.运行监控工具
`<jvm-monitor>/start.sh 127.0.0.1:9008 30s /home/user`


参数使用介绍: 运行`<jvm-monitor>/start.sh`获取
具体端口(例子中为9008)应该与中间件中配置的JVM参数`com.sun.management.jmxremote.port`保持一致

