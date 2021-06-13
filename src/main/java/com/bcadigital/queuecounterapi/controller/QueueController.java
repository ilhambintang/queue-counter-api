package com.bcadigital.queuecounterapi.controller;

import com.bcadigital.queuecounterapi.model.Queue;
import com.bcadigital.queuecounterapi.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QueueController {

    @Autowired
    private QueueRepository queueRepository;

    @GetMapping("/queues")
    public List<Queue> getAllQueues(){
        return queueRepository.findAll();
    }

    @GetMapping("/queues/{refNum}")
    public ResponseEntity<Queue> updateQueue(@PathVariable(value="refNum") Long referenceNumber) throws ResourceNotFoundException {
        Queue queue = queueRepository.findById(referenceNumber).orElseThrow(() -> new ResourceNotFoundException("Queue not found on :: "+referenceNumber));
        queue.setInQueue(false);
        queueRepository.save(queue);
        return ResponseEntity.ok().body(queue);
    }

}