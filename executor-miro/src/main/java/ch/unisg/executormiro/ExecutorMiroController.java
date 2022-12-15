package ch.unisg.executormiro;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ExecutorMiroController {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorMiroController.class);

    private final ExecutorMiroQueryService executorMiroQueryService;
    private final ExecutorMiroSearchEngineService executorMiroSearchEngineService;
    private static final String SPARQL_QUERY = "@prefix td: <https://www.w3.org/2019/wot/td#>.\n" +
            "select ?x\n" +
            "where { ?x a <https://interactions.ics.unisg.ch/mirogate#Mirogate> }";


    @GetMapping(path = "/executor/miro/{endpoint}")
    public ResponseEntity<String> queryMiro(@PathVariable("endpoint") String endpoint) {
        try {
            ThingDescription miroTD = executorMiroSearchEngineService.findTd(SPARQL_QUERY);
            String value = executorMiroQueryService.querySensor(miroTD, endpoint);
            if (value != null) {

                return new ResponseEntity<>("Miro card queries successfully, value is: " + value, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No value found for given endpoint '" + endpoint + "'", HttpStatus.BAD_REQUEST);
            }
        } catch (ThingDescriptionNotFoundException e) {
            LOGGER.error("Could not find a Thing Description for the miro card " + e.getMessage());
            return new ResponseEntity<>("could not find a Thing Description for the miro card" + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}

