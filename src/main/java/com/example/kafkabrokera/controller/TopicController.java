package com.example.kafkabrokera.controller;

import com.example.kafkabrokera.service.TopicService;
import com.example.kafkabrokera.service.TopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send("my-topic", msg);
    }

    @Autowired
    private Environment environment;

    @GetMapping("/checkConfig")
    public Map<String, String> checkConfig() {
        Map<String, String> props = new HashMap<String, String>();

        props.put("bootstrap.server", environment.getProperty("spring.kafka.consumer.bootstrap-servers"));

        return props;
    }

    @GetMapping("/send/{message}")
    public String send(@PathVariable String message) {

        this.sendMessage(message);

        return String.format("Message %s", message);
    }

    @GetMapping("/check")
    public Collection<String> seeTopics() throws ExecutionException, InterruptedException {
        return this.topicService.listTopics();
    }

    @GetMapping("/create/{topicName}")
    public String createTopic(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.createTopic(topicName, 1, (short) 1); //just try to make it work
    }
}
