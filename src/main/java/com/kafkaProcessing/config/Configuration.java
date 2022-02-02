package com.kafkaProcessing.config;

import org.apache.kafka.common.serialization.Serdes;
import java.util.Properties;
import static org.apache.kafka.streams.StreamsConfig.*;

public interface Configuration {
    static Properties getReceiveConfig() {
        Properties config = new Properties();
        config.put(APPLICATION_ID_CONFIG, "com.st.user-sync-back"); // TODO: Kafka Stream-Table-Inner-Join
        config.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return config;
    }
}
