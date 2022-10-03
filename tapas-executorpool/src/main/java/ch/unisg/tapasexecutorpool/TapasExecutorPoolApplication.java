package ch.unisg.tapasexecutorpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TapasExecutorPoolApplication {

	public static void main(String[] args) {
		SpringApplication tapasExecutorPoolApp = new SpringApplication(TapasExecutorPoolApplication.class);
        tapasExecutorPoolApp.run(args);
	}
}
