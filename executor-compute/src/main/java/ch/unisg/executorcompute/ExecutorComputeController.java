package ch.unisg.executorcompute;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.executorcompute.adapters.ComputationJsonRepresentation;

@RestController
public class ExecutorComputeController {

    @PostMapping(path = "/executor/compute/{computationType}")
    public ResponseEntity<String> compute(@PathVariable("computationType") String computationType, @RequestBody ComputationJsonRepresentation payload) {
        try {
            double sum;

            if(payload.getInputData() == null || payload.getInputData().equals("")){
                throw new NumberFormatException("inputData cannot be empty or 'null'");
            }

            String[] arrOfValues = payload.getInputData().split(",");
            switch (computationType){
                case "add" -> sum = add(arrOfValues);
                case "subtract" -> sum = subtract(arrOfValues);
                case "multiply" -> sum = multiply(arrOfValues);
                case "divide" -> sum = divide(arrOfValues);
                default -> throw new IllegalArgumentException("computation type not valid") ;
            }
            return new ResponseEntity<>("The result is " + sum, HttpStatus.OK);

        } catch (NumberFormatException e) {
            return new ResponseEntity<>("NumberFormatException - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public double add(String[] arrOfValues) {
        double sum = Double.NaN;
        for (String value : arrOfValues) {
            if (Double.isNaN(sum)) {
                sum = Double.parseDouble(value);
            } else {
                sum = sum + Double.parseDouble(value);
            }
        }
        return sum;
    }

    public double subtract(String[] arrOfValues) {
        double sum = Double.NaN;
        for (String value : arrOfValues) {
            if (Double.isNaN(sum)) {
                sum = Double.parseDouble(value);
            } else {
                sum = sum - Double.parseDouble(value);
            }
        }
        return sum;
    }

    private double multiply(String[] arrOfValues){
        double sum = Double.NaN;
        for (String value : arrOfValues) {
            if (Double.isNaN(sum)) {
                sum = Double.parseDouble(value);
            } else {
                sum = sum * Double.parseDouble(value);
            }
        }
        return sum;
    }

    private double divide(String[] arrOfValues) {
        double sum = Double.NaN;
        for (String value : arrOfValues) {
            if (Double.isNaN(sum)) {
                sum = Double.parseDouble(value);
            } else {
                sum = sum / Double.parseDouble(value);
            }
        }
        return sum;
    }
}
