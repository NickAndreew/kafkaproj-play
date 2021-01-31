package org.kafkaproj.play.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

/**
 * Utilities class, contains constants and methods
 *
 */
public class KafkaPlayAppUtils {

    /**
     * Object Mapper for working with Byte Arrays as with Java Typed Objects
     */
    public static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Method generating Unique Id
     * @return - UUID String
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
