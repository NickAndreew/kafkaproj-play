package org.kafkaproj.play.kafka.consumer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.kafkaproj.play.kafka.config.KafkaPlayAppKafkaConfig;
import org.kafkaproj.play.kafka.entity.KafkaPlayAppMessageEntity;
import org.kafkaproj.play.kafka.deserializer.KafkaPlayAppDeserializer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.apache.kafka.common.serialization.LongDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import java.util.Properties;
import java.util.Collections;
import java.util.function.Function;
import java.util.concurrent.CompletableFuture;

import static org.kafkaproj.play.kafka.config.KafkaPlayAppKafkaConfig.*;

/**
 * Kafka App Consumer class
 *
 */
public class KafkaPlayAppConsumer {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private final Logger logger = LoggerFactory.getLogger(KafkaPlayAppConsumer.class);

    /**
     * Kafka Consumer constructor
     *
     */
    public KafkaPlayAppConsumer() {
    }

    /**
     * Method creates preconfigured KafkaConsumer that is not yet listening on the topic,
     *  but has to be explicitly started by calling subscriber(topic) method with Topic Name as method argument.
     *
     * @return - org.apache.kafka.clients.consumer.Consumer
     */
    private Consumer<Long, KafkaPlayAppMessageEntity> createConsumer() {
        // init props
        final Properties props = new Properties();

        // add properties
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaPlayAppKafkaConfig.KAFKA_BROKERS_CONSUMER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaPlayAppKafkaConfig.GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaPlayAppDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KafkaPlayAppKafkaConfig.MAX_POLL_RECORDS);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaPlayAppKafkaConfig.OFFSET_RESET_EARLIER);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, REQUEST_TIMEOUT_MS_CONFIG_VALUE);
        props.put(ConsumerConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, DEFAULT_API_TIMEOUT_MS_CONFIG_VALUE);
        props.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, CONNECTIONS_MAX_IDLE_MS_CONFIG_VALUE);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        // init consumer
        final KafkaConsumer<Long, KafkaPlayAppMessageEntity> consumer = new KafkaConsumer<>(props);

        /**
         * TODO
         *  - check connection with Kafka Broker, now it does not throw exception,
         *      even if the Kafka Broker is not available!
          */

//        Future<Map<String, List<PartitionInfo>>> topics =

        if (new CompletableFuture<>().complete(consumer.listTopics())) {
            logger.info("Consumer successs.......................");
        } else {
            logger.info("Consumer failed.......................");
        }

        // return
        return consumer;
    }

    /**
     * Method starts listening on Kafka Topic provided in the method argument.
     *  Messages are being processed by predefined logic inside this method.
     *
     *  See also - startConsumingCustomLogic(String topicName, Function<KafkaPlayAppKafkaEntity, String> customLogic)
     *
     * @param topicName - Kafka Topic Name
     */
    public void startConsumingPredefinedLogic(final String topicName) {
        CompletableFuture.runAsync(() -> {
            // log
            logger.info("Beginning to consume from topic: " + topicName);

            //
            final Consumer<Long, KafkaPlayAppMessageEntity> consumer = createConsumer();

            consumer.subscribe(Collections.singleton(topicName));

            while (true) {
                ConsumerRecords<Long, KafkaPlayAppMessageEntity> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<Long, KafkaPlayAppMessageEntity> record : records) {
                    // log
                    logger.info("Message received in Predefined Logic Consumer: " + record.value());
                    // process
//                    kafkaAppMsgProcessor.processMessage(record.value());
                }
                consumer.commitAsync();
            }
        });
    }

    /**
     * Method allows to define custom processing logic on Topic Messages consuming process
     *
     * @param topicName - Kafka Topic name
     * @param customLogic - Callback function that is defined outside in caller method
     */
    public void startConsumingCustomLogic(final String topicName, final Function<KafkaPlayAppMessageEntity, String> customLogic) {
        CompletableFuture.runAsync(() -> {
            logger.info("Beginning to consume from topic: " + topicName);

            // async example..
            final Consumer<Long, KafkaPlayAppMessageEntity> consumer = createConsumer();
            consumer.subscribe(Collections.singleton(topicName));

            try {
                while (true) {
                    ConsumerRecords<Long, KafkaPlayAppMessageEntity> records = consumer.poll(Duration.ofMillis(100));

                    for (ConsumerRecord<Long, KafkaPlayAppMessageEntity> record : records) {
                        // log
                        logger.info("Message received in Custom Logic Consumer: " + record.value());
                        // process
                        customLogic.apply(record.value());
                    }
                    consumer.commitAsync();
                }
            } catch (Exception ex) {
                logger.error("Consumer failed, error: " + ex.getMessage());
                throw new RuntimeException(ex);
            } finally {
                consumer.close();
                logger.debug("Closed consumer");
            }
        });
    }
}
