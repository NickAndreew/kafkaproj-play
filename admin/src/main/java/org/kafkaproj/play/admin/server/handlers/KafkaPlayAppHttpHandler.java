package org.kafkaproj.play.admin.server.handlers;

import org.kafkaproj.play.admin.server.request.KafkaPlayAppHttpRequestEntity;

import org.kafkaproj.play.kafka.entity.KafkaPlayAppMessageEntity;
import org.kafkaproj.play.kafka.producer.KafkaPlayAppProducer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.apache.commons.text.StringEscapeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.kafkaproj.play.utils.KafkaPlayAppUtils.generateUUID;
import static org.kafkaproj.play.utils.KafkaPlayAppUtils.objectMapper;
import static org.kafkaproj.play.admin.config.KafkaPlayAppHttpConfig.GET;
import static org.kafkaproj.play.admin.config.KafkaPlayAppHttpConfig.POST;

/**
 * Http Handler
 *
 * Class defines logic for Http Server behavior
 *
 */
public class KafkaPlayAppHttpHandler implements HttpHandler {

    /**
     *  Logger - writes formatted logs to the specified resource (e.g. file, console, log database, etc.)
     */
    private final Logger logger = LoggerFactory.getLogger(KafkaPlayAppHttpHandler.class);

    /**
     *  Kafka Play App producer
     */
    private KafkaPlayAppProducer producer;

    private String topicName = "kafkaproj-playTopic1";

    /**
     *  Default constructor
     */
    public KafkaPlayAppHttpHandler() {
        this.producer = new KafkaPlayAppProducer();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // generate unique id for each request
        final String uid = generateUUID();

        String requestParamValue = null;
        if (GET.equals(httpExchange.getRequestMethod())) {
            // log
            requestParamValue = handleGetRequest(uid, httpExchange);
            logger.info("[{}] handled get request", uid);
        } else if (POST.equals(httpExchange.getRequestMethod())) {
            // log
            requestParamValue = handlePostRequest(uid, httpExchange);
            logger.info("[{}] handled post request", uid);
        }
        handleResponse(uid, httpExchange, requestParamValue);
        logger.info("[{}] Handled response: {}", uid, requestParamValue);
    }

    /**
     * Method for handling GET HTTP requests on the specified URI
     *
     * @param httpExchange - Basic Http Request Object reference
     * @return - operation result string
     */
    private String handleGetRequest(String uid, HttpExchange httpExchange) {
        // log
        logger.info("[{}] Received GET request", uid);

        // return
        return "My App is running...";
    }

    /**
     * Method for handling POST HTTP requests on the specified URI
     *
     * @param httpExchange - Basic Http Request Object reference
     * @return - operation result string
     */
    private String handlePostRequest(String uid, HttpExchange httpExchange) {
        logger.info("[{}] Received POST request", uid);

        InputStream inputStream = httpExchange.getRequestBody();
        try {
            // decode request body
            KafkaPlayAppHttpRequestEntity entity =
                    objectMapper.readValue(inputStream.readAllBytes(), KafkaPlayAppHttpRequestEntity.class);

            // log
            logger.info("[{}] Decoded entity: {}", uid, entity.toString());

            // create msg
            KafkaPlayAppMessageEntity kafkaAppMessage1 =
                    new KafkaPlayAppMessageEntity(
                            entity.getParam1(),
                            entity.getParam2(),
                            entity.getParam3(),
                            entity.getParam4(),
                            entity.getParam5());

            // log
            logger.info("[{}] Trying to send message : {} to topic {}", uid, entity.toString(), topicName);

            // send msg
            producer.sendMessageAsync(uid, topicName, kafkaAppMessage1);

            // log
            logger.info("[{}] Processed POST request - {}", uid, kafkaAppMessage1);
        } catch (IOException ex) {
            // log
            logger.error("[{}] Exception thrown when tried to deserialize request body, error: {}", uid, ex);
        }

        return "POST request processed";
    }

    /**
     * Method handling the HTTP Response for Requests
     *
     * @param uid - unique id
     * @param httpExchange - request/response object reference
     * @param requestParamValue - response param value
     * @throws IOException - thrown on OI operations
     */
    private void handleResponse(String uid, HttpExchange httpExchange, String requestParamValue) throws IOException {
        // init output stream
        OutputStream outputStream = httpExchange.getResponseBody();

        // init string builder with provided result value
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append(requestParamValue);

        // encode result value to html
        String htmlResponse = StringEscapeUtils.escapeHtml3(htmlBuilder.toString());

        // send response header
        httpExchange.sendResponseHeaders(200, htmlResponse.length());

        // write response body to output stream
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    // TODO - move Converter to a better place
    private KafkaPlayAppHttpRequestEntity convert(String uid, KafkaPlayAppMessageEntity entity) {
        logger.info("[{}] Converting entity : {} to KafkaPlayAppHttpRequestEntity", uid);
        return new KafkaPlayAppHttpRequestEntity(
                entity.getParam1(),
                entity.getParam2(),
                entity.getParam3(),
                entity.getParam4(),
                entity.getParam5());
    }
}
