package org.kafkaproj.play.app.demo;

import org.kafkaproj.play.app.service.KafkaPlayAppService;
import org.kafkaproj.play.admin.server.KafkaPlayAppHttpServer;
import org.kafkaproj.play.kafka.consumer.KafkaPlayAppConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.kafkaproj.play.utils.KafkaPlayAppUtils.generateUUID;

/**
 * Demo Class
 *
 *  - Created for demonstration purpose
 */
public class KafkaPlayAppDemoService {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private static final Logger logger = LoggerFactory.getLogger(KafkaPlayAppDemoService.class);

    /**
     * App Kafka Consumer
     *
     *  - Created for demonstration purpose
     */
    private KafkaPlayAppConsumer kafkaConsumer;

    /**
     * App Admin HTTP Controller Server
     *
     *  - Created for demonstration purpose
     */
    private KafkaPlayAppHttpServer httpServer;

    /**
     * App Service
     *
     *  - Created for demonstration purpose
     */
    private KafkaPlayAppService service;

    /**
     * Demo Class constructor
     *
     *  - Created for demonstration purpose
     */
    public KafkaPlayAppDemoService() {
        try {
            // init demo dependencies
            this.kafkaConsumer = new KafkaPlayAppConsumer();
            this.httpServer = new KafkaPlayAppHttpServer();
            this.service = new KafkaPlayAppService();
        } catch (Exception ex) {
            logger.error("Failed to initialize Demo service, Error: " + ex.getMessage());
        }
    }

    /**
     * Main Demo method
     *
     *  - Created for demonstration purpose
     *
     * @throws InterruptedException
     */
    public void startServiceDemo() throws InterruptedException {
        // start Kafka consumer
        kafkaConsumer.startConsumingCustomLogic("kafkaproj-playTopic1", (msg) -> {
            // generate unique id for each message
            final String uid = generateUUID();
            // log
            logger.info("[{}] Received message: {}", uid, msg.toString());
            // process
            service.processMessage(uid, msg);
            // return
            return msg.toString();
        });

        // start http server
        httpServer.startAdminServer();
    }
}
