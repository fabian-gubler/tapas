package ch.unisg.executormiro;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpRequest;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpResponse;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.security.SecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import ch.unisg.ics.interactions.wot.td.vocabularies.WoTSec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExecutorRobotCherryBotService {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorRobotCherryBotService.class);
    private static final String OPERATOR_NAME = "Valentin Berger";
    private static final String OPERATOR_EMAIL = "valentin.berger@student.unisg.ch";
    private static final String REGISTER_OPERATOR_ACTION = "https://interactions.ics.unisg.ch/cherrybot#RegisterOperator";
    private static final String REMOVE_OPERATOR_ACTION = "https://interactions.ics.unisg.ch/cherrybot#RemoveOperator";
    private static final String SET_ROBOT_TARGET_ACTION = "https://interactions.ics.unisg.ch/cherrybot#SetTarget";

    public void moveRobot(ThingDescription cherryBotTD) throws IOException {
        String token = registerOperator(cherryBotTD);
        setRobotTarget(cherryBotTD, token);

        // Wait for the robot to finish moving then remove the operator
        try {
            Thread.sleep(30000);
            deleteOperator(cherryBotTD, token);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String registerOperator(ThingDescription robotTd) throws IOException {
        Optional<ActionAffordance> action = robotTd.getFirstActionBySemanticType(REGISTER_OPERATOR_ACTION);

        Optional<Form> form = Optional.empty();

        if (action.isPresent()) {
            form = action.get().getFirstForm();
        }

        if (form.isPresent()) {
            TDHttpRequest request = new TDHttpRequest(form.get(), TD.invokeAction);

            Map<String, Object> body = new HashMap<>();
            body.put("http://xmlns.com/foaf/0.1/Name", OPERATOR_NAME);
            body.put("http://xmlns.com/foaf/0.1/Mbox", OPERATOR_EMAIL);
            Optional<DataSchema> bodySchema = action.get().getInputSchema();
            request.setObjectPayload((ObjectSchema) bodySchema.get(), body);

            TDHttpResponse response = request.execute();
            LOGGER.info("Received response with status code: " + response.getStatusCode());
            LOGGER.info("Received response with headers: " + response.getHeaders());

            if(response.getStatusCode() == 200) {
                String locationHeader = response.getHeaders().get("location");
                String token = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
                LOGGER.info("Received token: " + token);
                return token;
            }

            LOGGER.error("Error when registering operator: " + response.getPayload().get());
        }
        return "";
    }

    private void setRobotTarget(ThingDescription robotTd, String token){
        try {
            Optional<ActionAffordance> action = robotTd.getFirstActionBySemanticType(SET_ROBOT_TARGET_ACTION);
            Optional<Form> form = Optional.empty();

            if (action.isPresent()) {
                form = action.get().getFirstForm();
            }

            if (form.isPresent()) {
                TDHttpRequest request = new TDHttpRequest(form.get(), TD.invokeAction);

                // some random target
                Map<String, Object> coordinate = new HashMap<>();
                coordinate.put("x", 500);
                coordinate.put("y", 100);
                coordinate.put("z", 200);
                Map<String, Object> rotation = new HashMap<>();
                rotation.put("roll", 180);
                rotation.put("pitch", 20);
                rotation.put("yaw", 10);
                Map<String, Object> target = new HashMap<>();
                target.put("rotation", rotation);
                target.put("coordinate", coordinate);
                Map<String, Object> body = new HashMap<>();
                body.put("target", target);
                body.put("speed", 50);

                Optional<DataSchema> bodySchema = action.get().getInputSchema();
                request.setObjectPayload((ObjectSchema) bodySchema.get(), body);

                Optional<SecurityScheme> securityScheme = robotTd.getFirstSecuritySchemeByType(WoTSec.APIKeySecurityScheme);
                request.setAPIKey((APIKeySecurityScheme) securityScheme.get(), token);

                TDHttpResponse response = request.execute();
                LOGGER.info("Moved robot: " + response.getStatusCode());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteOperator(ThingDescription robotTd, String token){
        Optional<ActionAffordance> action = robotTd.getFirstActionBySemanticType(REMOVE_OPERATOR_ACTION);

        Optional<Form> form = Optional.empty();

        if (action.isPresent()) {
            form = action.get().getFirstForm();
        }

        if (form.isPresent()) {
            TDHttpRequest request = new TDHttpRequest(form.get(), TD.invokeAction);
            String targetURI =  request.getTarget();

            // remove everything after last slash from target URI
            targetURI = targetURI.substring(0, targetURI.lastIndexOf("/") + 1) + token;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(targetURI))
                .DELETE()
                .build();

            try {
                HttpResponse response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                LOGGER.info("Operator removed with status code: " + response.statusCode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
