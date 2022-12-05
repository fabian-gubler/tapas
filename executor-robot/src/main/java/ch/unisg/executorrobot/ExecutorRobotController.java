package ch.unisg.executorrobot;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpRequest;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpResponse;
import ch.unisg.ics.interactions.wot.td.io.TDGraphReader;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.security.SecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import ch.unisg.ics.interactions.wot.td.vocabularies.WoTSec;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ExecutorRobotController {
    private static final String SPARKL_QUERY = "@prefix td: <https://www.w3.org/2019/wot/td#>.\n" +
            "select ?x\n" +
            "where { ?x a td:Thing }";

    @PostMapping(path = "/executor/robot/")
    public ResponseEntity<String> triggerRobot() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.interactions.ics.unisg.ch/search/searchEngine"))
                .header("content-type", "application/sparql-query")
                .POST(HttpRequest.BodyPublishers.ofString(SPARKL_QUERY))
                .build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            //TODO get URI from xml
            String token = getToken("https://yggdrasil.interactions.ics.unisg.ch/environments/61/workspaces/102/artifacts/cherrybot");
            moveRobot(token, "https://yggdrasil.interactions.ics.unisg.ch/environments/61/workspaces/102/artifacts/cherrybot");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>("Robot triggered", HttpStatus.OK);
    }

    private String getToken(String tdUri) {
        try {
            ThingDescription td = TDGraphReader.readFromURL(ThingDescription.TDFormat.RDF_TURTLE, tdUri);
            System.out.println(td.getActions());

            Optional<ActionAffordance> action = td.getActionByName("registerOperator");
            System.out.println(action);

            Optional<Form> form = Optional.empty();

            if (action.isPresent()) {
                form = action.get().getFirstForm();
            }

            if (form.isPresent()) {
                TDHttpRequest request = new TDHttpRequest(form.get(), TD.invokeAction);

                Map<String, Object> body = new HashMap<>();
                body.put("http://xmlns.com/foaf/0.1/Name", "Atilla Gueven");
                body.put("http://xmlns.com/foaf/0.1/Mbox", "atilla.gueven@student.unisg.ch");
                Optional<DataSchema> bodySchema = action.get().getInputSchema();
                request.setObjectPayload((ObjectSchema) bodySchema.get(), body);

                TDHttpResponse response = request.execute();
                System.out.println("Received response with status code: " + response.getStatusCode());
                System.out.println("Received response with headers: " + response.getHeaders());

                String locationHeader = response.getHeaders().get("location");
                String token = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
                System.out.println(token);
                return token;
            }

            return "";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void moveRobot(String token, String tdUri) {
        try {
            ThingDescription td = TDGraphReader.readFromURL(ThingDescription.TDFormat.RDF_TURTLE, tdUri);
            System.out.println(td.getActions());

            Optional<ActionAffordance> action = td.getActionByName("setTarget");
            System.out.println(action);

            Optional<Form> form = Optional.empty();

            if (action.isPresent()) {
                form = action.get().getFirstForm();
            }

            if (form.isPresent()) {
                TDHttpRequest request = new TDHttpRequest(form.get(), TD.invokeAction);

                Map<String, Object> coordinate = new HashMap<>();
                coordinate.put("x", 500);
                coordinate.put("y", 100);
                coordinate.put("z", 200);
                Map<String, Object> rotation = new HashMap<>();
                rotation.put("roll", 180);
                rotation.put("pitch", 0);
                rotation.put("yaw", 0);
                Map<String, Object> target = new HashMap<>();
                target.put("rotation", rotation);
                target.put("coordinate", coordinate);
                Map<String, Object> body = new HashMap<>();
                body.put("target", target);
                body.put("speed", 50);

                Optional<DataSchema> bodySchema = action.get().getInputSchema();
                request.setObjectPayload((ObjectSchema) bodySchema.get(), body);

                Optional<SecurityScheme> securityScheme = td.getFirstSecuritySchemeByType(WoTSec.APIKeySecurityScheme);
                request.setAPIKey((APIKeySecurityScheme) securityScheme.get(), token);

                TDHttpResponse response = request.execute();
                System.out.println("Received response with status code: " + response.getStatusCode());
                System.out.println("Received response with headers: " + response.getHeaders());

        }

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }

}