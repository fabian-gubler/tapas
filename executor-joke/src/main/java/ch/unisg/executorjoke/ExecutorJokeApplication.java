package ch.unisg.executorjoke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Configuration
public class ExecutorJokeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecutorJokeApplication.class, args);
	}

}
