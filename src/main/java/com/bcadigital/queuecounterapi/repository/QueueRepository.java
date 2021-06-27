package com.bcadigital.queuecounterapi.repository;

import com.bcadigital.queuecounterapi.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface QueueRepository extends JpaRepository<Queue, Long> {
    Integer countByTimestampLessThanAndInQueue(Date timestamp, Boolean in_queue);
}

