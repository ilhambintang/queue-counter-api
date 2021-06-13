package com.bcadigital.queuecounterapi.repository;

import com.bcadigital.queuecounterapi.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository extends JpaRepository<Queue, Long> {
}
