package com.kafkaProcessing.services.kafka;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;

import static com.kafkaProcessing.config.Configuration.getReceiveConfig;

public abstract class BaseProcessing {
    public final static StreamsBuilder builder = new StreamsBuilder();

    public BaseProcessing() {
        process();
        run();
    }

    private void run() {
        KafkaStreams streams = new KafkaStreams(builder.build(), getReceiveConfig());
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public abstract void process();
}
