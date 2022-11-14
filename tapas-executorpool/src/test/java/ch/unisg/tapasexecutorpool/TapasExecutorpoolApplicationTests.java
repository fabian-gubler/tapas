package ch.unisg.tapasexecutorpool;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TapasExecutorpoolApplicationTests {
    //TODO Rewrite tests with database mock -> the test fail now because there is no database when they run in docker
    /*
    private final AddNewExecutorToExecutorPoolService addNewExecutorToExecutorPool = new AddNewExecutorToExecutorPoolService();
    private final MatchExecutorToReceivedTaskService matchExecutorToReceivedTask = new MatchExecutorToReceivedTaskService(new PublishNewTaskExecutionAdapter());
    private final RemoveExecutorFromExecutorPoolService removeExecutorFromExecutorPool = new RemoveExecutorFromExecutorPoolService();

    private final Executor.Endpoint endpoint = new Executor.Endpoint("http://localhost:8084/excutors/api");
    private final Executor.ExecutorType executorType = new Executor.ExecutorType(Executor.Type.api);

    private final ExecutorPool executorPool = ExecutorPool.getExecutorPool();
    AddNewExecutorToExecutorPoolCommand command = new AddNewExecutorToExecutorPoolCommand(endpoint, executorType);
    private final String executorId = addNewExecutorToExecutorPool.addNewExecutorToExecutorPool(command);

    @Test
	void contextLoads() {
    }

    @Test
    void testExecutorAdd() {
        Optional<Executor> executor = executorPool.retrieveExecutorById(new Executor.ExecutorId(executorId));
        assertTrue(executor.isPresent());
    }

    @Test
    void testExecutorTypeAndEndpoint() {
        Optional<Executor> executor = executorPool.retrieveExecutorById(new Executor.ExecutorId(executorId));
        assertEquals(executor.get().getExecutorType().getValue(), executorType.getValue());
        assertEquals(executor.get().getEndpoint().getValue(), endpoint.getValue());
    }

    @Test
    void testExecutorMatching() {
        String taskType = "api";
        String taskLocation = "testLocation";
        String inputData = null;

        MatchExecutorToReceivedTaskCommand command = new MatchExecutorToReceivedTaskCommand(taskType, taskLocation, inputData);

        Optional<Executor> matchedExecutor = matchExecutorToReceivedTask.matchExecutorToReceivedTask(command);

        assertTrue(matchedExecutor.isPresent());
    }
    @Test
    void testExecutorRemoval() {
        RemoveExecutorFromExecutorPoolCommand command = new RemoveExecutorFromExecutorPoolCommand(new Executor.ExecutorId(executorId));

        Boolean isRemoved = removeExecutorFromExecutorPool.removeExecutorFromExecutorPool(command);

        Optional<Executor> executor = executorPool.retrieveExecutorById(new Executor.ExecutorId(executorId));
        assertTrue(executor.isEmpty());
    }*/
}
