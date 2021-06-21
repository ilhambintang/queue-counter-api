package com.bcadigital.queuecounterapi.controller;

import com.bcadigital.queuecounterapi.model.Queue;
import com.bcadigital.queuecounterapi.model.QueueResponse;
import com.bcadigital.queuecounterapi.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @PutMapping("/queue/{refNum}")
    public ResponseEntity<String> updateQueuePut(@RequestBody Map<String, Object> payload, @PathVariable(value="refNum") Long referenceNumber) throws ResourceNotFoundException{
        Queue queue = queueRepository.findById(referenceNumber).orElseThrow(() -> new ResourceNotFoundException("Queue not found on :: "+referenceNumber));
        queue.setInQueue((Boolean) payload.get("inqueue"));
        queueRepository.save(queue);
        return ResponseEntity.ok().build();
    }


    // Get Latest Queue Number (Mobile)
    @GetMapping("/queue/{refNum}")
    public ResponseEntity<QueueResponse> getQueueNumber(@PathVariable(value = "refNum") Long refNumber) {
        List<Queue> queues = queueRepository.findAll(Sort.by("timestamp").ascending());

        QueueResponse queueResponse = new QueueResponse();

        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getReferenceNumber() == refNumber) {
                queueResponse.setRefNumber(queues.get(i).getReferenceNumber());
                queueResponse.setTimeStamp(queues.get(i).getTimestamp());
                queueResponse.setInQueue(queues.get(i).isInQueue());
                queueResponse.setQueueNumber(i);
            }
        }

        return ResponseEntity.ok().body(queueResponse);
    }

    // InCall()
    @PostMapping("/queue")
    public ResponseEntity<QueueResponse> insertQueue(@RequestParam(value = "refNumber") Long refNumber) {
        Calendar calendar = Calendar.getInstance();
        // Insert
        queueRepository.save(new Queue(refNumber, calendar.getTime(), true));

        // Get Latest Queue Number
        List<Queue> queues = queueRepository.findAll(Sort.by("timestamp").ascending());

        QueueResponse queueResponse = new QueueResponse();

        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getReferenceNumber() == refNumber) {
                queueResponse.setRefNumber(queues.get(i).getReferenceNumber());
                queueResponse.setTimeStamp(queues.get(i).getTimestamp());
                queueResponse.setInQueue(queues.get(i).isInQueue());
                queueResponse.setQueueNumber(i);
            }
        }

        return ResponseEntity.ok().body(queueResponse);
    }

}
