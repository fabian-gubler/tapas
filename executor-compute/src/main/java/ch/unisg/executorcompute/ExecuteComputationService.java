package ch.unisg.executorcompute.adapters;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExecuteComputationService {
    @Async
    public void compute(String computationType, ComputationJsonRepresentation payload, String returnLocation) {
        try {
            Thread.sleep(10000);

        } catch (InterruptedException e){
            System.out.println("interruped");
        }
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
            System.out.println(returnLocation);
            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> map = new HashMap<>();
            map.put("success", "true");
            map.put("outputData", "The result is: " + sum);

            ResponseEntity<Void> response = restTemplate.postForEntity(returnLocation, map, Void.class);

        } catch (IllegalArgumentException e ){
            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> map = new HashMap<>();
            map.put("success", "false");
            map.put("outputData", "Input Data not valid");

            ResponseEntity<Void> response = restTemplate.postForEntity(returnLocation, map, Void.class);
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
