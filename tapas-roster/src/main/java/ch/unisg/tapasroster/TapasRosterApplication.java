package ch.unisg.tapasroster;

import ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb.RosterAssignmentRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
Starting point of the Spring application
 */

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = RosterAssignmentRepository.class)
@EnableAsync
public class TapasRosterApplication {

    // main method of the roster microservice to run the application
	public static void main(String[] args) {
		SpringApplication tapasRosterApp = new SpringApplication(TapasRosterApplication.class);
        tapasRosterApp.run(args);
	}

}
