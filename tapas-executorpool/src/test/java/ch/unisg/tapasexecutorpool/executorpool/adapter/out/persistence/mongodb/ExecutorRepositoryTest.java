package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import static org.assertj.core.api.Assertions.assertThat;

import ch.unisg.tapasexecutorpool.executorpool.application.service.AddNewExecutorToExecutorPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
@Import({ ExecutorPersistenceAdapter.class, ExecutorMapper.class })
public class ExecutorRepositoryTest {

	@Autowired
	ExecutorPersistenceAdapter executorPersistenceAdapter;

	@Autowired
	ExecutorRepository executorRepository;

    @Autowired
    AddNewExecutorToExecutorPoolService addNewExecutorToExecutorPoolService;

	/*@Before
	public void setUp() throws Exception {

		String executorType = "TEST_TYPE";
		String endpoint = "TEST_ENDPOINT";

        Executor executorStub = Executor.createExecutorWithTypeAndEnpoint(new Executor.ExecutorType(executorType),
                new Executor.Endpoint(endpoint));

        executorPersistenceAdapter.addExecutor(executorStub);
	}*/

    /*@Test
    void addsNewExecutor(){
        Executor.ExecutorType executorType = new Executor.ExecutorType("EXECUTOR_TYPE");
        Executor.Endpoint endpoint = new Executor.Endpoint("EXECUTOR_ENDPOINT");

        AddNewExecutorToExecutorPoolCommand command = new AddNewExecutorToExecutorPoolCommand(endpoint, executorType);

        addNewExecutorToExecutorPoolService.addNewExecutorToExecutorPool(command);


        MongoExecutorDocument retrieveDoc = executorRepository.findByExecutorType(executorType.getValue());

        assertThat(retrieveDoc.executorType).isEqualTo(executorType);
    }*/

	/*@Test
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
	}*/

}
