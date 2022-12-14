package ch.unisg.executormiro;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ExecutorRobotController {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorRobotController.class);
    private final ExecutorRobotCherryBotService executorRobotCherryBotService;
    private final ExecutorRobotSearchEngineService executorRobotSearchEngineService;
    private static final String SPARQL_QUERY = "@prefix td: <https://www.w3.org/2019/wot/td#>.\n" +
            "select ?x\n" +
            "where { ?x a td:Thing }";

    private static final String ROBOT_TITLE = "cherryBot";

    @PostMapping(path = "/executor/robot/")
    public ResponseEntity<String> triggerRobot() {
        try {
           ThingDescription cherryBotTD = executorRobotSearchEngineService.findTd(SPARQL_QUERY, ROBOT_TITLE);
           executorRobotCherryBotService.moveRobot(cherryBotTD);
        } catch (ThingDescriptionNotFoundException e) {
            LOGGER.error("Could not find a Thing Description for the robot " + ROBOT_TITLE, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("Robot moved", HttpStatus.OK);
    }



}