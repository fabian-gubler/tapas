package ch.unisg.tapasexecutorpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
Starting point of the Spring application
 */

@SpringBootApplication
public class TapasExecutorPoolApplication {

    // main method of the the executor pool microservice to run the application
	public static void main(String[] args) {
		SpringApplication tapasExecutorPoolApp = new SpringApplication(TapasExecutorPoolApplication.class);
        tapasExecutorPoolApp.run(args);
	}
}
