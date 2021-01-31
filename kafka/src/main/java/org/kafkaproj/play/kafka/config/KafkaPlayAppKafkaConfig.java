package org.kafkaproj.play.kafka.config;

public interface KafkaPlayAppKafkaConfig {

    String KAFKA_BROKERS = System.getenv().get("KAFKA_CONNECT_URL");

    String CLIENT_ID = "client1";

    Integer MESSAGE_COUNT_DEMO = 1000;

    String TOPIC_NAME_DEMO = "demo";

    String GROUP_ID_CONFIG = "consumerGroup1";

    Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;

    String OFFSET_RESET_LATEST = "latest";

    String OFFSET_RESET_EARLIER = "earliest";

    Integer MAX_POLL_RECORDS = 1;

    Integer REQUEST_TIMEOUT_MS_CONFIG_VALUE = 10000;
    Integer DELIVERY_TIMEOUT_MS_CONFIG_VALUE = 10000;
    Integer MAX_BLOCK_MS_CONFIG_VALUE = 10000;
    Integer DEFAULT_API_TIMEOUT_MS_CONFIG_VALUE = 5000;
    Integer CONNECTIONS_MAX_IDLE_MS_CONFIG_VALUE = 600000;
    Integer BATCH_SIZE_VALUE = 300000;

}
