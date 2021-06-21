package com.bcadigital.queuecounterapi.model;

import java.util.Date;

public class QueueResponse {
    private long refNumber;
    private Date timeStamp;
    private boolean inQueue;
    private int queueNumber;

    public QueueResponse() {

    }

    public QueueResponse(long refNumber, Date timeStamp, boolean inQueue, int queueNumber) {
        this.refNumber = refNumber;
        this.timeStamp = timeStamp;
        this.inQueue = inQueue;
        this.queueNumber = queueNumber;
    }

    public long getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(long refNumber) {
        this.refNumber = refNumber;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }
}
