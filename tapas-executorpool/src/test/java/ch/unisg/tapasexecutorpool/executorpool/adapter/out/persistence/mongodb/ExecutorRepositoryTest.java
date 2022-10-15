package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.context.annotation.Import;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

@AutoConfigureDataMongo
@Import({ ExecutorPersistenceAdapter.class, ExecutorMapper.class })
public class ExecutorRepositoryTest {

	@Autowired
	ExecutorPersistenceAdapter executorPersistenceAdapter;

	@Autowired
	ExecutorRepository executorRepository;

	@Before
	public void setUp() throws Exception {

		String executorType = "TEST_TYPE";
		String endpoint = "TEST_ENDPOINT";

        Executor executorStub = Executor.createExecutorWithTypeAndEnpoint(new Executor.ExecutorType(executorType),
                new Executor.Endpoint(endpoint));

        executorPersistenceAdapter.addExecutor(executorStub);
	}

	@Test
	public void retrieveItems() {

		String executorType = "TEST_TYPE";
		String endpoint = "TEST_ENDPOINT";

        Executor executorStub = Executor.createExecutorWithTypeAndEnpoint(new Executor.ExecutorType(executorType),
                new Executor.Endpoint(endpoint));

        executorPersistenceAdapter.addExecutor(executorStub);
        MongoExecutorDocument retrievedDocument = executorRepository.findByExecutorType(executorType);

        assertThat(retrievedDocument.executorType).isEqualTo(executorType);
        assertThat(retrievedDocument.endpoint).isEqualTo(endpoint);
	}

	@Test
	public void shouldBeNotEmpty() {
		assertThat(executorRepository.findAll()).isNotEmpty();
	}

}
