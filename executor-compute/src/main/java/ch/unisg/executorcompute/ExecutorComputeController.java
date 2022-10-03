package ch.unisg.executorcompute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.executorcompute.adapters.ComputationJsonRepresentation;


@RestController
public class ExecutorComputeController {

    @PostMapping(path = "/executor/compute", consumes = {ComputationJsonRepresentation.MEDIA_TYPE})
    public String compute(@RequestBody ComputationJsonRepresentation payload) {

        double valueA = payload.getValueA();
        double valueB = payload.getValueB();
        String result = "The result is ";

        switch (payload.getType()) {
            case "ADD" -> result += valueA + valueB;
            case "SUBTRACT" -> result += valueA - valueB;
            case "MULTIPLY" -> result += valueA * valueB;
            case "DIVIDE" -> result += valueA / valueB;
            default -> result = "invalid computation type";
        }

        return result;
    }
}
