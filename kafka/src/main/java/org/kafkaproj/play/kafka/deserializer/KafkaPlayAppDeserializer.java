package org.kafkaproj.play.kafka.deserializer;

import org.kafkaproj.play.kafka.entity.KafkaPlayAppMessageEntity;

import org.apache.kafka.common.serialization.Deserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.kafkaproj.play.utils.KafkaPlayAppUtils.objectMapper;

/**
 * Kafka App Custom Deserializer
 *
 */
public class KafkaPlayAppDeserializer implements Deserializer<KafkaPlayAppMessageEntity> {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private final Logger logger = LoggerFactory.getLogger(KafkaPlayAppDeserializer.class);

    @Override
    public KafkaPlayAppMessageEntity deserialize(String topic, byte[] data) {
        KafkaPlayAppMessageEntity kafkaAppMessage = null;
        try {
            kafkaAppMessage = objectMapper.readValue(data, KafkaPlayAppMessageEntity.class);
        } catch (Exception exception) {
            logger.error("Error in deserializing bytes : " + exception);
        }
        return kafkaAppMessage;
    }
}
