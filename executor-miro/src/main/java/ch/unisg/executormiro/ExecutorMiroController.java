package ch.unisg.executormiro;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.config.CoapConfig;
import org.eclipse.californium.elements.config.UdpConfig;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ExecutorMiroController {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorMiroController.class);

    private final String coapServer = "coap://130.82.171.10:5683";

    @PostMapping(path = "/executor/miro/{endpoint}")
    public ResponseEntity<String> triggerRobot(@PathVariable("endpoint") String endpoint) {
        try {
            CoapConfig.register();
            UdpConfig.register();

            CoapClient client = new CoapClient(coapServer + "/" + endpoint);
            Response response = client.get().advanced();

            Gson gson = new Gson();
            ResponsePayload responsePayload = gson.fromJson(response.getPayloadString(), ResponsePayload.class);

            System.out.println(responsePayload.value);

            client.shutdown();

            return new ResponseEntity<>("Data received, value is: " + responsePayload.value.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Request error", HttpStatus.BAD_REQUEST);
        }
    }
}

class ResponsePayload {
    Float value;
}