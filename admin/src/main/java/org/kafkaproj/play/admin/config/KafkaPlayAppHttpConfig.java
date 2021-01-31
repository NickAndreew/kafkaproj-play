package org.kafkaproj.play.admin.config;

public interface KafkaPlayAppHttpConfig {

    String GET = "GET";
    String POST = "POST";

    String DEFAULT_HTTP_HOST = "0.0.0.0";
    Integer DEFAULT_HTTP_PORT = 8080;

    Integer DEFAULT_THREAD_POOL_SIZE = 10;
    Integer DEFAULT_HTTP_BACKLOG_CONSTANT = 0;
}
