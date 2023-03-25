package com.example.kafkabrokera.service;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TopicService {

    public String createTopic(String topicName, int numPartitions, short replicationFactor) throws ExecutionException, InterruptedException;

    public Collection<String> listTopics() throws ExecutionException, InterruptedException;

    public void listenMessageInTopic(String message);
}

