package com.bcadigital.queuecounterapi.controller;

import com.bcadigital.queuecounterapi.model.Queue;
import com.bcadigital.queuecounterapi.model.QueueResponse;
import com.bcadigital.queuecounterapi.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class QueueController {
    @Autowired
    private QueueRepository queueRepository;


    // Updating in_queue value of a particular entry to FALSE
    // Called during onEnded and answeredCall event
    @PutMapping("/queue/{refNum}")
    public ResponseEntity<String> updateQueuePut(@RequestBody Map<String, Object> payload, @PathVariable(value="refNum") Long referenceNumber)
            throws ResourceNotFoundException{
        Queue queue = queueRepository.findById(referenceNumber).orElseThrow(() ->
                new ResourceNotFoundException("Queue not found on :: "+referenceNumber));
        queue.setInQueue((Boolean) payload.get("inqueue"));
        queueRepository.save(queue);
        return ResponseEntity.ok().build();
    }


    // Get the latest queue number of a particular entry
    // Called periodically
    @GetMapping("/queue/{refNum}")
    public ResponseEntity<?> getQueueNumber(@PathVariable(value = "refNum") Long referenceNumber)
            throws ResourceNotFoundException{
        Queue queue = queueRepository.findById(referenceNumber).orElseThrow(() ->
                new ResourceNotFoundException("Queue not found on :: "+referenceNumber));
        Integer queueNumber = countQueueNumber(queue,referenceNumber);
        QueueResponse queueResponse = new QueueResponse(queue.getReferenceNumber(), queue.getTimestamp(), queue.isInQueue(), queueNumber);
        return ResponseEntity.ok().body(queueResponse);
    }


    // Insert new queue entry into the database
    // Called during InCall event
    @PostMapping("/queue")
    public ResponseEntity<QueueResponse> insertQueue(@RequestBody Map<String, Object> payload) {
        Long referenceNumber = Long.valueOf(payload.get("reference_number").toString());
        Calendar calendar = Calendar.getInstance();
        Queue queue = new Queue(referenceNumber, calendar.getTime(), true);
        queueRepository.save(queue);
        Integer queueNumber = countQueueNumber(queue,referenceNumber);
        QueueResponse queueResponse = new QueueResponse(queue.getReferenceNumber(), queue.getTimestamp(), queue.isInQueue(), queueNumber);
        return ResponseEntity.ok().body(queueResponse);
    }

    // Count the queue number
    private Integer countQueueNumber(Queue queue, Long referenceNumber){
        Integer queueNumber;
        if (queue.isInQueue()){
            queueNumber = (queueRepository.countByTimestampLessThanAndInQueue(queue.getTimestamp(),true)+1);
        } else {
            queueNumber = 0;
        }
        return queueNumber;
    }
}
