package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

@DataMongoTest
@Import({ ExecutorPersistenceAdapter.class, ExecutorMapper.class })
public class ExecutorRepositoryTest {

	@Autowired
	ExecutorPersistenceAdapter executorPersistenceAdapter;

	@Autowired
	ExecutorRepository executorRepository;

	// populate db
	@Before
	public void setUp() throws Exception {

		String executorType = "TEST_TYPE";
		String endpoint = "TEST_ENDPOINT";
		String executorId = "TEST_ID";

		executorRepository.save(new MongoExecutorDocument(executorId, endpoint, executorType));
	}

	@Test
	public void shouldBeNotEmpty() {
		assertThat(executorRepository.findAll()).isNotEmpty();
	}


}
