package com.example.kafkabrokera.service;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.errors.UnknownTopicOrPartitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Autowired
    private Environment environment;

    private AdminClient adminClient;

    //guarantee: adminClient Autowired
    @PostConstruct
    private void init() {
        this.adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Override
    public String createTopic(String topicName, int numPartitions, short replicationFactor) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        adminClient.createTopics(Collections.singleton(newTopic));
        return String.format("Topic %s created", topicName);
    }
    public List<String> listTopics() throws ExecutionException, InterruptedException {

        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);
        ListTopicsResult listTopicsResult = adminClient.listTopics(listTopicsOptions);

        Collection<TopicListing> topics = listTopicsResult.listings().get();

        return topics.stream().map(topic -> topic.name()).collect(Collectors.toList());
    }

    @Override
    @KafkaListener(topics = "my-topic", groupId = "group-1") //EnableKafka allows Listener
    public void listenMessageInTopic(String message) {
        log.info("Message intercepted in topic my-topic: {}", message);
    }
}
