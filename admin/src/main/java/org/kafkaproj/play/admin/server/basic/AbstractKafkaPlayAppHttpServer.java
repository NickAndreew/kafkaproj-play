package org.kafkaproj.play.admin.server.basic;

import org.kafkaproj.play.admin.server.handlers.KafkaPlayAppHttpHandler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.kafkaproj.play.admin.config.KafkaPlayAppHttpConfig.*;

/**
 * Kafka Play Admin HTTP Server Controller Base class
 *
 */
public class AbstractKafkaPlayAppHttpServer {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     *
     */
    private final Logger logger = LoggerFactory.getLogger(AbstractKafkaPlayAppHttpServer.class);

    /**
     * App Server Host Address
     *
     */
    private String host;

    /**
     * App Server Port Number
     *
     */
    private Integer port;

    /**
     * Executor for asynchronous request handling
     *  Used by HttpServer.setExecutor(executor) see method
     *  - AbstractKafkaPlayAppHttpServer#startServer(java.lang.String, java.lang.Integer)
     *   below.
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * Http Server reference;
     *
     */
    private HttpServer server;

    /**
     * Basic Constructor
     *  Initializes Server Properties with Default values
     *
     */
    public AbstractKafkaPlayAppHttpServer() {
        this.host = DEFAULT_HTTP_HOST;
        this.port = DEFAULT_HTTP_PORT;
    }

    /**
     * Customizable Utility Constructor
     *
     * @param host - Server Host Address
     * @param port - Server Port Number
     */
    public AbstractKafkaPlayAppHttpServer(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Method used in constructors for initializing and starting HTTP server.
     *
     * @return - Boolean result of operation
     */
    protected Boolean startServer(final Map<String, HttpHandler> contextMap) {
        try {
            // init executor and server
            this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
            this.server = HttpServer.create(new InetSocketAddress(host, port), DEFAULT_HTTP_BACKLOG_CONSTANT);
            this.server.setExecutor(threadPoolExecutor);

            // set api routes
            contextMap.forEach((entryKey, entryValue) -> {
                this.server.createContext(entryKey, entryValue);
            });

            // log
            logger.info("Trying to start server at {}:{}", host, port);
            this.server.start();

            return true;
        } catch (IOException ex) {
            logger.error("Exception thrown while starting HTTP Server: {}", ex.getMessage());
            return false;
        }
    }

//    public Map<String, HttpHandler> getContextMap() {
//        return contextMap;
//    }
//
//    public void setContextMap(Map<String, HttpHandler> contextMap) {
//        this.contextMap = contextMap;
//    }
}
