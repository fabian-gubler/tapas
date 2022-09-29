package ch.unisg.tapastasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TapasTasksApplication {

	public static void main(String[] args) {
		SpringApplication tapasTasksApp = new SpringApplication(TapasTasksApplication.class);
		tapasTasksApp.run(args);



	}

}
