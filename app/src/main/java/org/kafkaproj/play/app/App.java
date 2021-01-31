package org.kafkaproj.play.app;

import org.kafkaproj.play.app.demo.KafkaPlayAppDemoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application main class
 */
public class App {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Application main method
     *
     * @param args - console arguments
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        logger.info("Hello World");

        logger.info("System properties KAFKA_CONNECT_URL: {}", System.getenv().get("KAFKA_CONNECT_URL"));

        KafkaPlayAppDemoService demoService = new KafkaPlayAppDemoService();
        demoService.startServiceDemo();
     }
}
