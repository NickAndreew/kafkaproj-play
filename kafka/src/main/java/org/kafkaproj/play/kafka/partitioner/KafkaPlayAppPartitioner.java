package org.kafkaproj.play.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Kafka App Partitioner class
 * TODO - NOT IMPLEMENTED
 */
public class KafkaPlayAppPartitioner implements Partitioner {

    private static final int PARTITION_COUNT = 50;

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        final int keyInt = Integer.parseInt(key.toString());
        return keyInt % PARTITION_COUNT;
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public void close() {

    }
}
