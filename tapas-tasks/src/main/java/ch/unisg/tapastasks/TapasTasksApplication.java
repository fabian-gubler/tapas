package ch.unisg.tapastasks;

import ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = TaskRepository.class)
public class TapasTasksApplication {

    // The following Bean would be needed to activate MongoDB's transaction manager for the @Transactional annotation.
    // However, the MongoDB then needs to be reconfigured to work with a replica set.
    // https://www.digitalocean.com/community/tutorials/how-to-use-transactions-in-mongodb
    /**
    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
    **/
	public static void main(String[] args) {
		SpringApplication tapasTasksApp = new SpringApplication(TapasTasksApplication.class);
		tapasTasksApp.run(args);

	}

}
