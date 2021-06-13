package com.bcadigital.queuecounterapi.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="queue_counter")
public class Queue {
    @Id
    private long referenceNumber;

    @Column(name="timestamp")
    @CreatedDate
    private Date timestamp;

    @Column(name="in_queue")
    private boolean inQueue;

    public Queue() {
        super();
    }

    public Queue(long referenceNumber, Date timestamp, boolean inQueue) {
        this.referenceNumber = referenceNumber;
        this.timestamp = timestamp;
        this.inQueue = inQueue;
    }

    public long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }
}
