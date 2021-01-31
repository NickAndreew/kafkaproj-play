package org.kafkaproj.play.db.config;

import org.kafkaproj.play.db.entity.KafkaPlayAppDbEntity;

public interface KafkaPlayAppDbConfig {

    String DEFAULT_MONGO_DB_HOST = System.getenv().get("MONGO_CONNECT_HOST");
    Integer DEFAULT_MONGO_DB_PORT = Integer.valueOf(System.getenv().get("MONGO_CONNECT_PORT"));
//    Integer DEFAULT_MONGO_DB_PORT = 27017;
    String DEFAULT_MONGO_DB_DATABASE = "data";

    String DEFAULT_MONGO_DB_TABLE = "KafkaPlayAppDbEntity_Table";

    Class DEFUALT_CLASS_TYPE = KafkaPlayAppDbEntity.class;
}
