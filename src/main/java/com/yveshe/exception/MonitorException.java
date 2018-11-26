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
package com.yveshe.exception;

public class MonitorException extends Exception {
    private static final long serialVersionUID = 8959981733651943021L;
    private int type = DEFAULT;

    public static final int DEFAULT = 0x0;
    public static final int PERSIST = 0x100;
    public static final int PERSIST_NAME_EXIST = 0x101;
    public static final int INVALIED_BO = 0x200;
    public static final int NOT_FOUND_BO = 0x300;

    public MonitorException(String msg) {
        super(msg);
    }

    public MonitorException(int type, String msg) {
        super(msg);
        this.type = type;
    }

    public MonitorException(MonitorException causeException) {
        super(causeException);
        this.type = causeException.type;
    }

    public MonitorException(String msg, MonitorException causeException) {
        super(msg, causeException);
        this.type = causeException.type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        switch (type) {
        case PERSIST:
            return "Persist Exception : " + super.toString();
        case INVALIED_BO:
            return "Invalied BO : " + super.toString();
        case NOT_FOUND_BO:
            return "Not-Found BO : " + super.toString();
        default:
            return super.toString();
        }
    }

}
