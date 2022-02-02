package com.kafkaProcessing.utils;

public interface KafkaUtil {
    String USER_REGISTERED_TOPIC = "com.st.users.registration.event.user-registered";
    String TAGS_CHANGED_TOPIC    = "com.st.users.registration.event.tags-changed";
    String USER_ROLLBACK_TOPIC   = "com.st.users.registration.event.user-rollback";
    long FIVE_SECOND = 5;
    long TEEN_SECOND = 10;
    long FIFTEEN_SECOND = 15;
}
