package ch.unisg.executorcompute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Configuration
@EnableAsync
public class ExecutorComputeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecutorComputeApplication.class, args);
	}

}
