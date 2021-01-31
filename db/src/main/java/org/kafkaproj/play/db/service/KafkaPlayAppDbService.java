package org.kafkaproj.play.db.service;

import org.kafkaproj.play.db.entity.KafkaPlayAppDbEntity;
import org.kafkaproj.play.db.service.basic.BasicMongoDbService;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.FindOneAndReplaceOptions;

import com.mongodb.client.result.DeleteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.kafkaproj.play.db.config.KafkaPlayAppDbConfig.DEFAULT_MONGO_DB_TABLE;

/**
 * Kafka Play App MongoDB Database Service
 *
 * Implements basic CRUD operations.
 *
 *  - CURRENTLY IMPLEMENTED WITH JUST ONE TABLE, COULD BE MODIFIED TO WORK WITH MORE REPOSITORIES!
 *
 */
public class KafkaPlayAppDbService extends BasicMongoDbService<KafkaPlayAppDbEntity> {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private static Logger logger = LoggerFactory.getLogger(KafkaPlayAppDbService.class);

    /**
     * KafkaPlayApp Database Service Constructor
     *
     *  Default constructor for demo initialization, with default configs
     */
    public KafkaPlayAppDbService() {
        super();
        initCollection(DEFAULT_MONGO_DB_TABLE, KafkaPlayAppDbEntity.class);
    }

    /**
     * KafkaPlayApp Database Service Constructor
     *
     *  Configurable constructor for class initialization, with default table
     */
    public KafkaPlayAppDbService(String host, Integer port, String database) {
        super(host, port, database);
        initCollection(DEFAULT_MONGO_DB_TABLE, KafkaPlayAppDbEntity.class);
    }

    /**
     * KafkaPlayApp Database Service Constructor
     *
     *  Configurable constructor for class initialization
     */
    public KafkaPlayAppDbService(final String host, final Integer port, final String database, final String table) {
        super(host, port, database);
        initCollection(table, KafkaPlayAppDbEntity.class);
    }

    /**
     * Method to insert Transferable Data Object into Mongo Db
     *
     * @param entity - Data Object
     */
    public void insert(final KafkaPlayAppDbEntity entity) {
        logger.info("Trying to insert : {}", entity);
        collection.insertOne(entity);
    }

    /**
     * Method to update Transferable Data Object in Mongo Db
     *
     * @param entity - Data Object
     */
    public KafkaPlayAppDbEntity update(final KafkaPlayAppDbEntity entity) {
        logger.info("Trying to update : {}", entity);
        FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
        return collection.findOneAndReplace(Filters.eq("param1", entity.getParam1()), entity, returnDocAfterReplace);
    }

    /**
     * Method to delete/remove Transferable Data Object from Mongo Db
     *
     * @param entity - Data Object
     */
    public DeleteResult delete(final KafkaPlayAppDbEntity entity) {
        logger.info("Trying to delete : {}", entity);

        return collection.deleteOne(Filters.eq("_id", entity.getId()));
    }

    /**
     * Method to get/obtain Transferable Data Object from Mongo Db
     *
     * @param entity - Data Object
     */
    public KafkaPlayAppDbEntity get(final KafkaPlayAppDbEntity entity) {
        logger.info("Trying to get : {}", entity);

        return collection.find(Filters.eq("param1", entity.getParam1())).first();
    }
}
