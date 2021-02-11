package org.kafkaproj.play.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.kafkaproj.play.kafka.config.KafkaPlayAppKafkaConfig;
import org.kafkaproj.play.kafka.entity.KafkaPlayAppMessageEntity;
import org.kafkaproj.play.kafka.serializer.KafkaPlayAppSerializer;

import org.apache.kafka.common.serialization.LongSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.*;

import static org.kafkaproj.play.kafka.config.KafkaPlayAppKafkaConfig.*;

/**
 * Kafka App Producer Service class
 *
 */
public class KafkaPlayAppProducer {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private final Logger logger = LoggerFactory.getLogger(KafkaPlayAppProducer.class);

    /**
     * Kafka Producer Class for sending Messages to Kafka Topic
     *
     */
    private final Producer<Long, KafkaPlayAppMessageEntity> producer;

    /**
     * Basic Constructor
     *
     */
    public KafkaPlayAppProducer() {
        this.producer = createProducer();
    }

    /**
     * Method creates Kafka Producer Instance without topic set,
     *
     * @return - org.apache.kafka.clients.producer.KafkaProducer
     */
    private Producer<Long, KafkaPlayAppMessageEntity> createProducer() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaPlayAppKafkaConfig.KAFKA_CONNECT_PRODUCER);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaPlayAppKafkaConfig.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaPlayAppSerializer.class);
//        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, KafkaPlayAppPartitioner.class);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, REQUEST_TIMEOUT_MS_CONFIG_VALUE);
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, DELIVERY_TIMEOUT_MS_CONFIG_VALUE);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, MAX_BLOCK_MS_CONFIG_VALUE);
        props.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, CONNECTIONS_MAX_IDLE_MS_CONFIG_VALUE);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, BATCH_SIZE_VALUE);

        return new KafkaProducer<>(props);
    }

    /**
     * Method for producing messages to Ka
     * fka Topic, blocks sending thread to receive return on operation
     *
     * @param topicName - Kafka Topic name
     * @param message - Message to send to Kafka Topic
     */
    public void sendMessageSync(final String topicName, final KafkaPlayAppMessageEntity message) {
        //
        final ProducerRecord<Long, KafkaPlayAppMessageEntity> producerRecord = new ProducerRecord<>(topicName, message);

        try {
            RecordMetadata metadata = producer.send(producerRecord).get();
            logger.info("Record sent with partition {} with offset {}", metadata.partition(), metadata.offset());
        } catch (ExecutionException | InterruptedException ex) {
            logger.error("Exception thrown on trying to send message synchronously. Error: {}", ex.getMessage());
        }
    }

    /**
     * Method for producing messages to Kafka Topic asynchronously.
     *
     * @param topicName - Kafka Topic name
     * @param message - Message to send to Kafka Topic
     */
    public void sendMessageAsync(final String uid, final String topicName, final KafkaPlayAppMessageEntity message) {
        CompletableFuture.runAsync(() -> {
            logger.info("[{}] Producer.sendMessageAsync running...", uid);

            final ProducerRecord<Long, KafkaPlayAppMessageEntity> producerRecord = new ProducerRecord<>(topicName, message);
            logger.info("[{}] Created Producer Record...", uid);

            logger.info("[{}] Trying to send producer record...", uid);

            producer.send(producerRecord, (metadata, ex) -> {
                if (ex != null) {
                    logger.info("[{}] Wrting to Kafka failed with an exception : {}", uid, ex.getCause());
                    throw new RuntimeException(ex);
                } else {
                    logger.info("[{}] Record sent with partition {} with offset {} has been sent successfully, presumably.",
                            uid, metadata.partition(), metadata.offset());
                }
            });
        });
    }
}
