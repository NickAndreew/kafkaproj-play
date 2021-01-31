package org.kafkaproj.play.app.service;

import org.kafkaproj.play.kafka.entity.KafkaPlayAppMessageEntity;

import org.kafkaproj.play.db.service.KafkaPlayAppDbService;
import org.kafkaproj.play.db.entity.KafkaPlayAppDbEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application's main business processing logic class
 *
 */
public class KafkaPlayAppService {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private final Logger logger = LoggerFactory.getLogger(KafkaPlayAppService.class);

    /**
     *  Database Service for persisting the records to the db
     *
     */
    private KafkaPlayAppDbService dbService;

    /**
     * Basic constructor
     *
     */
    public KafkaPlayAppService() {
        this.dbService = new KafkaPlayAppDbService();
    }

    /**
     * Configurable constructor
     *
     * @param dbService - custom Database Service
     */
    public KafkaPlayAppService(KafkaPlayAppDbService dbService) {
        this.dbService = dbService;
    }

    /**
     * Main processing method of the Service
     *
     * @param entity - Object to process
     */
    public void processMessage(String uid, KafkaPlayAppMessageEntity entity) {
        // log
        logger.info("[{}] processMessage(entity) invoked. Entity: {}", uid, entity.toString());

        /**
         * TODO
         *   - convert message entity to db entity
         */
        KafkaPlayAppDbEntity dbEntity = new KafkaPlayAppDbEntity(
                uid,
                entity.getParam1(),
                entity.getParam2(),
                entity.getParam3(),
                entity.getParam4(),
                entity.getParam5()
        );
        // log
        logger.info("[{}] Trying to save record to database... {}", uid, dbEntity);
        // processing logic
         // save to db
        dbService.insert(dbEntity);
        // log
        logger.info("[{}] Successfully stored record to database... {}", uid, dbEntity);
    }
}
