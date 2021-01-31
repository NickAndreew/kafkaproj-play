package org.kafkaproj.play.admin.server;

import org.kafkaproj.play.admin.server.basic.AbstractKafkaPlayAppHttpServer;

import com.sun.net.httpserver.HttpHandler;

import org.kafkaproj.play.admin.server.handlers.KafkaPlayAppHttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.kafkaproj.play.admin.config.KafkaPlayAppHttpConfig.DEFAULT_HTTP_HOST;
import static org.kafkaproj.play.admin.config.KafkaPlayAppHttpConfig.DEFAULT_HTTP_PORT;

/**
 * Kafka Play App HTTP Server for admin service access
 *
 */
public class KafkaPlayAppHttpServer extends AbstractKafkaPlayAppHttpServer {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private static Logger logger = LoggerFactory.getLogger(KafkaPlayAppHttpServer.class);

    /**
     * Http Server context reference.
     *  Keys - request mapping addresses, e.g. - '/app/status'
     *  Values - request handler classes
     *
     */
    private Map<String, HttpHandler> contextMap;

    /**
     *  Default Constructor
     *  Initializes server parameters on default configurations
     *
     *  see also:
     *  - org.kafkaproj.play.admin.config.KafkaPlayAppHttpConfig,
     *  - org.kafkaproj.play.admin.server.basic.AbstractKafkaPlayAppHttpServer
     */
    public KafkaPlayAppHttpServer() {
        super();
        // init map
        this.contextMap = new HashMap<>();

        // define and put uri endpoints and corresponding handlers to the map
        this.contextMap.put("/app/admin", new KafkaPlayAppHttpHandler());
//        this.contextMap.put("/app/status", new SomeOtherHandler());
//        ..
    }

    /**
     * Method to register all the controller handlers and start the HTTP Server
     *
     */
    public void startAdminServer() {
        // log
        logger.info("Starting admin HTTP server.. ");
        // start server with provided context
        startServer(this.contextMap);
    }

    public Map<String, HttpHandler> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<String, HttpHandler> contextMap) {
        this.contextMap = contextMap;
    }
}
