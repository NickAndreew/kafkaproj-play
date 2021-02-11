package org.kafkaproj.play.db.service.basic;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import static org.kafkaproj.play.db.config.KafkaPlayAppDbConfig.DEFAULT_MONGO_DB_HOST;
import static org.kafkaproj.play.db.config.KafkaPlayAppDbConfig.DEFAULT_MONGO_DB_PORT;
import static org.kafkaproj.play.db.config.KafkaPlayAppDbConfig.DEFAULT_MONGO_DB_DATABASE;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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
     * Mongo Db address host
     *
     */
    private final String host;

    /**
     * Mongo Db address port
     *
     */
    private final Integer port;

    /**
     * Mongo Db address host
     *
     */
    private final String database;

    /**
     * Mongo Db Database Connection Object Reference
     *
     */
    private MongoDatabase mongoDatabase;

    /**
     * CodecRegistry includes a codec that handles the translation to and from BSON for our POJOs
     *
     */
    private final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());

    /**
     * Default codec registry, contains all the default codecs.
     * They can handle all the major types in Java-like Boolean, Double, String, BigDecimal, etc.
     *
     */
    private final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            pojoCodecRegistry);

    /**
     * Basic default constructor
     *
     */
    public BasicMongoDbService() {
        this.host = DEFAULT_MONGO_DB_HOST;
        this.port = DEFAULT_MONGO_DB_PORT;
        this.database = DEFAULT_MONGO_DB_DATABASE;
        initConnection();
    }

    /**
     * Custom constructor
     *
     * @param host - connection host address
     * @param port - connection port address
     * @param database - database name
     */
    public BasicMongoDbService(String host, Integer port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
        initConnection();
    }

    /**
     * Method initializing connection with MongoDb
     *
     * @return - boolean result
     */
    private Boolean initConnection() {
        try {
            MongoClient mongoClient = new MongoClient(host, port);
            this.mongoDatabase = mongoClient.getDatabase(database).withCodecRegistry(codecRegistry);
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
