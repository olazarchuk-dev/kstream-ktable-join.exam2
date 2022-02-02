package com.kafkaProcessing.services;

import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.time.Duration;

import static com.kafkaProcessing.utils.KafkaUtil.FIFTEEN_SECOND;

public class KafkaProcessingService {
    private KTable<String, String> userRegisteredTbl;
    private KTable<String, String> tagsChangedTbl;
    private KTable<String, String> userRollbackTbl;

    public KafkaProcessingService(KTable<String, String> userRegisteredTbl, KTable<String, String> tagsChangedTbl, KTable<String, String> userRollbackTbl) {
        this.userRegisteredTbl = userRegisteredTbl;
        this.tagsChangedTbl = tagsChangedTbl;
        this.userRollbackTbl = userRollbackTbl;
    }

    public KStream<String, String> getRegisteredJoin() {
        return tagsChangedTbl.toStream()
                .join(tagsChangedTbl, (lVal, rVal) -> rVal)                  // TODO: stream.table.inner.join-com.st.users.registration.event.tags-changed-STATE-STORE-000...-changelog
                .join(userRegisteredTbl, (lVal, rVal) -> rVal + "," + lVal); // TODO: stream.table.inner.join-com.st.users.registration.event.user-registered-STATE-STORE-000...-changelog
    }

    public KStream<String, String> getRollbackJoin(KStream<String, String> registeredJoin) {
        return userRollbackTbl.toStream()
                .join(userRollbackTbl, (lVal, rVal) -> rVal)                 // TODO: stream.table.inner.join-com.st.users.registration.event.user-rollback-STATE-STORE-000...-changelog
                .join(registeredJoin,
                      (lVal, rVal) -> rVal + "," + lVal,
                      JoinWindows.of(Duration.ofSeconds(FIFTEEN_SECOND)));
    }
}
