package org.kafkaproj.play.admin.server.response;

import org.kafkaproj.play.kafka.entity.KafkaPlayAppMessageEntity;

/**
 * This class is supposed to represent json data response for Admin requests
 *
 */
public class KafkaPlayAppHttpResponseEntity {

    private String uuid;
    private String operationResult;
    private KafkaPlayAppMessageEntity entity;

    public KafkaPlayAppHttpResponseEntity() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public KafkaPlayAppMessageEntity getEntity() {
        return entity;
    }

    public void setEntity(KafkaPlayAppMessageEntity entity) {
        this.entity = entity;
    }
}
