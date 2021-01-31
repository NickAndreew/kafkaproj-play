package org.kafkaproj.play.kafka.serializer;

import org.kafkaproj.play.kafka.entity.KafkaPlayAppMessageEntity;

import org.apache.kafka.common.serialization.Serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.kafkaproj.play.utils.KafkaPlayAppUtils.objectMapper;

/**
 * Kafka App Serializer class
 *
 */
public class KafkaPlayAppSerializer implements Serializer<KafkaPlayAppMessageEntity> {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private final Logger logger = LoggerFactory.getLogger(KafkaPlayAppSerializer.class);

    @Override
    public byte[] serialize(String topic, KafkaPlayAppMessageEntity data) {
        try {
            logger.info("Trying to serialize msg: {}", data);
            return objectMapper.writeValueAsBytes(data);
        } catch (IOException ex) {
            logger.error("exception thrown: {}", ex.getMessage());
            throw new RuntimeException("Exception occurred: {}" + ex.getMessage());
        }
    }
}
