package org.kafkaproj.play.db.service.basic;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.kafkaproj.play.db.config.KafkaPlayAppDbConfig.*;

/**
 * Mongo Database Service Abstract Base Class
 *
 * @param <T> - type class of the entities stored in the table
 */
public abstract class BasicMongoDbService<T> {

    /**
     * Mondgo Db Table Collection Object, used for executing CRUD and other query operations
     *
     */
    protected MongoCollection<T> collection;

    /**
     * Mongo Db Database Connection Object Reference
     *
     */
    private MongoDatabase mongoDatabase;

    /**
     * CodecRegistry includes a codec that handles the translation to and from BSON for our POJOs
     *
     */
    private CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());

    /**
     * Default codec registry, contains all the default codecs.
     * They can handle all the major types in Java-like Boolean, Double, String, BigDecimal, etc.
     *
     */
    private CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            pojoCodecRegistry);

    public BasicMongoDbService() {
        initConnection(DEFAULT_MONGO_DB_HOST, DEFAULT_MONGO_DB_PORT, DEFAULT_MONGO_DB_DATABASE);
    }

    public BasicMongoDbService(String host, Integer port, String database) {
        initConnection(host, port, database);
    }

    /**
     * Method initializing connection with MongoDb
     *
     * @param host - MongoDb host address
     * @param port - MongoDb port number
     * @param database - MongoDb Database Name
     * @return
     */
    private Boolean initConnection(String host, Integer port, String database) {
        try {
            MongoClient mongoClient = new MongoClient(host, port);
            this.mongoDatabase = mongoClient.getDatabase(database);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Method initializing collection / table object reference for working with database Table
     *
     * @param tableName - MongoDb Table Name
     * @param classType - MongoDb Table Entity Object class type
     * @return - boolean result of operation
     */
    protected Boolean initCollection(String tableName, Class<T> classType) {
        try {
            this.collection = this.mongoDatabase.getCollection(tableName, classType);
            return true;
        } catch (Exception ex) {
            // log error
            System.out.println("Exception thrown when tried to initialize collection (table) reference : " + ex.getMessage());
            return false;
        }
    }
}
