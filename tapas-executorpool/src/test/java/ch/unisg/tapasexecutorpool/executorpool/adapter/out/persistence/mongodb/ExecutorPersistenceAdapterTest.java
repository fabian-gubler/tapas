package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import static org.assertj.core.api.Assertions.assertThat;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorNotFoundError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
@Import({ ExecutorPersistenceAdapter.class, ExecutorMapper.class })
public class ExecutorPersistenceAdapterTest {

    @Autowired
    private ExecutorPersistenceAdapter executorPersistenceAdapter;

    @Autowired
    private ExecutorRepository executorRepository;

    @Test
    void addsNewExecutor(){
        Executor.ExecutorType executorType = new Executor.ExecutorType("EXECUTOR_TYPE");
        Executor.Endpoint endpoint = new Executor.Endpoint("EXECUTOR_ENDPOINT");

        Executor.ExecutorId testExecutorId = new Executor.ExecutorId(UUID.randomUUID().toString());
        Executor executor = new Executor(executorType, endpoint, testExecutorId);

        executorPersistenceAdapter.addExecutor(executor);

        MongoExecutorDocument retrieveDoc = executorRepository.findByExecutorId(testExecutorId.getValue());

        assertThat(retrieveDoc.executorType).isEqualTo(executorType.getValue());
    }

    @Test
	void retrieveItems() {

		String executorType = "TEST_TYPE";
		String endpoint = "TEST_ENDPOINT";

        Executor executorStub = Executor.createExecutorWithTypeAndEnpoint(new Executor.ExecutorType(executorType),
                new Executor.Endpoint(endpoint));

        MongoExecutorDocument mongoExecutorDocument = new MongoExecutorDocument(executorStub.getExecutorId().getValue(), executorStub.getEndpoint().getValue(), executorStub.getExecutorType().getValue());

        executorRepository.insert(mongoExecutorDocument);

        try {
            Executor retrievedExecutor = executorPersistenceAdapter.loadExecutor(executorStub.getExecutorId());
            assertThat(retrievedExecutor.getExecutorType().getValue()).isEqualTo(executorType);
            assertThat(retrievedExecutor.getEndpoint().getValue()).isEqualTo(endpoint);
        } catch (ExecutorNotFoundError e) {
            System.out.println(e);
        }
	}

}
