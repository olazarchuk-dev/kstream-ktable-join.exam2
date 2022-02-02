package com.kafkaProcessing;

import com.kafkaProcessing.services.KafkaProcessingService;
import com.kafkaProcessing.services.kafka.BaseProcessing;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.kafkaProcessing.utils.KafkaUtil.*;
import static com.kafkaProcessing.utils.FormatDate.*;
import static com.kafkaProcessing.utils.ArrayUtil.*;

/**
 * @see https://mydeveloperplanet.com/2019/10/30/kafka-streams-joins-explored
 * ***
 *
 * ### Step 4
 *
 * > /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic com.st.users.registration.event.user-registered --property "parse.key=true" --property "key.separator=:"
 * >:
 * >1:u1
 * >2:u2
 * >3:u3
 *
 * > /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic com.st.users.registration.event.tags-changed --property "parse.key=true" --property "key.separator=:"
 * >:
 * >1:t1
 * >2:t2
 * >3:t3
 *
 * > /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic com.st.users.registration.event.user-rollback --property "parse.key=true" --property "key.separator=:"
 * >:
 * >1:r1
 * >2:r2
 * >3:r3
 *
 * Automatic create Joined-Topic(s) on Kafka:
 * > stream.table.inner.join-com.st.users.registration.event.tags-changed-STATE-STORE-000...-changelog
 * > stream.table.inner.join-com.st.users.registration.event.user-registered-STATE-STORE-000...-changelog
 * > stream.table.inner.join-com.st.users.registration.event.user-rollback-STATE-STORE-000...-changelog
 * > stream.table.inner.join-KSTREAM-JOINOTHER-000...-store-changelog
 * > stream.table.inner.join-KSTREAM-JOINTHIS-000...-store-changelog
 */
@SpringBootApplication
public class KafkaProcessingApplication extends BaseProcessing {
    private static KafkaProcessingService processingService;

    @Override
    public void process() {
        KStream<String, String> registeredJoin = processingService.getRegisteredJoin();           // TODO: stream.table.inner.join-KSTREAM-JOINOTHER-000...-store-changelog
        registeredJoin.foreach(new ForeachAction<String, String>() {
            public void apply(String key, String value) {
                String[] values = value.split(",");
                findFirst(values).ifPresent(_value ->
                        System.out.println(dateNow() + " --- USER REGISTERED: '" + _value + "'"));
                findLast(values).ifPresent(_value ->
                        System.out.println(dateNow() + " --- TAGS CHANGED:    '" + _value + "'"));
            }
        });

        KStream<String, String> rollbackJoin = processingService.getRollbackJoin(registeredJoin); // TODO: stream.table.inner.join-KSTREAM-JOINTHIS-000...-store-changelog
        rollbackJoin.foreach(new ForeachAction<String, String>() {
            public void apply(String key, String value) {
                String[] values = value.split(",");
                findLast(values).ifPresent(_value ->
                        System.out.println(dateNow() + " --- USER ROLLBACK:   '" + _value + "'"));
            }
        });
    }

    public static void main(String[] args) {
        processingService = new KafkaProcessingService(builder.table(USER_REGISTERED_TOPIC), builder.table(TAGS_CHANGED_TOPIC), builder.table(USER_ROLLBACK_TOPIC));
        SpringApplication.run(KafkaProcessingApplication.class, args);
    }
}
