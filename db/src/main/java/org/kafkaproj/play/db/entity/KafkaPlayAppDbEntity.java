package org.kafkaproj.play.db.entity;

import org.kafkaproj.play.model.db.KafkaPlayAppDbEntityInterface;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * KafkaPlayApp Data Object for MongoDb database
 *
 */
public class KafkaPlayAppDbEntity implements KafkaPlayAppDbEntityInterface {

    private ObjectId id;

    @BsonProperty(value = "uid")
    private String uid;
    @BsonProperty(value = "param1")
    private String param1;
    @BsonProperty(value = "param2")
    private String param2;
    @BsonProperty(value = "param3")
    private String param3;
    @BsonProperty(value = "param4")
    private String param4;
    @BsonProperty(value = "param5")
    private String param5;

    public KafkaPlayAppDbEntity(String uid, String param1, String param2, String param3, String param4, String param5) {
        this.uid = uid;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }
}
