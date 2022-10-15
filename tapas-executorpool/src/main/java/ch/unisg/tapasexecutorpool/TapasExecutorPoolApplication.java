package ch.unisg.tapasexecutorpool;

import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorPersistenceAdapter;
import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorRepository;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
Starting point of the Spring application
 */

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = ExecutorRepository.class)
public class TapasExecutorPoolApplication {

    // main method of the the executor pool microservice to run the application
	public static void main(String[] args) {
		SpringApplication tapasExecutorPoolApp = new SpringApplication(TapasExecutorPoolApplication.class);
        tapasExecutorPoolApp.run(args);
	}

}
