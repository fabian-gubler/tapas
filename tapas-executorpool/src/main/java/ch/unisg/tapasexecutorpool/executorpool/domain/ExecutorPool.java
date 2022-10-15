package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.*;


/**
 * This class is an aggregate root, which is the only object that is loaded in the repository of the client code.
 */
public class ExecutorPool {

    @Getter
    private final ExecutorPoolName executorPoolName;

    @Getter
    private final ListOfExecutors listOfExecutors;

    @Getter
    private final TaskTypeExecutorTypeMapper taskTypeExecutorTypeMapper;

    /**
     * The exeutorPool object can not be changed once it is created.
     * There is only one ExecutorPool in the project to load in the different Executors.
     */
    private static final ExecutorPool executorPool = new ExecutorPool(new ExecutorPoolName("tapas-executorpool-group3"));

    /**
     * constructor of the ExecutorPool where the linked list is created in.
     * The Mapper is created to match the different tasks to the appropriate executor.
     *
     * @param executorpoolname
     */

    private ExecutorPool(ExecutorPoolName executorpoolname) {
        this.executorPoolName = executorpoolname;
        this.listOfExecutors = new ListOfExecutors(new LinkedList<Executor>());

        this.taskTypeExecutorTypeMapper = new TaskTypeExecutorTypeMapper(new HashMap<String, Executor.ExecutorType>());

        // initialize tasktype to executortype mapper -> initial values are added
        // this takes over the function of the roster for the moment
        // in a simple hashmap for every tasktype as string there is a matching executor type assigned
        /*this.taskTypeExecutorTypeMapper.value.put("joke", Executor.E.JOKE);
        this.taskTypeExecutorTypeMapper.value.put("compute-add", Executor.Type.COMPUTE_ADD);
        this.taskTypeExecutorTypeMapper.value.put("compute-divide", Executor.Type.COMPUTE_DIVIDE);
        this.taskTypeExecutorTypeMapper.value.put("compute-multiply", Executor.Type.COMPUTE_MULTIPLY);
        this.taskTypeExecutorTypeMapper.value.put("compute-subtract", Executor.Type.COMPUTE_SUBTRACT);*/
    }

    // subclass for the ExecutorPool class, which provides automatically a Getter and Setter method.
    @Value
    public static class ExecutorPoolName {
        private String value;
    }

    /**
     * method to add a new executor to the executor pool with the appropriate type and endpoint
     * @param endpoint
     * @param executorType
     * @return Executor
     */
    public Executor addNewExecutorToExecutorPoolWithTypeAndEndpoint(Executor.Endpoint endpoint, Executor.ExecutorType executorType) {
        Executor newExecutor = Executor.createExecutorWithTypeAndEnpoint(executorType, endpoint);
        this.addNewExecutorToPool(newExecutor);

        return newExecutor;
    }

    // method to return the executorPool.
    public static ExecutorPool getExecutorPool() {
        return executorPool;
    }

    /**
     * method to remove an executor by ID from the executorPool
     * @param executorId
     * @return - debug mesage if the method worked
     */
    public Boolean removeExecutorFromExecutorPoolById(Executor.ExecutorId executorId) {
        Optional<Executor> executor = this.retrieveExecutorById(executorId);

        if (executor.isPresent()) {
            this.listOfExecutors.getValue().remove(executor.get());
            return true;
        }

        return false;
    }

    /**
     * retrieve the Executor by ID out of the List of executors.
     * @param id
     * @return - returns executor object as an optional class which can be returned empty, that you do not have to check if object is null or not.
     */
    public Optional<Executor> retrieveExecutorById(Executor.ExecutorId id) {
        for (Executor executor : listOfExecutors.value) {
            if (executor.getExecutorId().getValue().equalsIgnoreCase(id.getValue())) {
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    /**
     * method to find an Executor by task type
     * @param taskType
     * @return - returns executor object as an optional class which can be returned empty, that you do not have to check if object is null or not.
     */
    public Optional<Executor> findAvailableExecutorFromTaskTypeString(String taskType) {
        for (Executor executor : listOfExecutors.value) {
            if (executor.getExecutorType().getValue().equals(this.taskTypeExecutorTypeMapper.value.get(taskType))) {
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    /**
     * method to add new executor to the pool.
     * @param newExecutor
     */
    // todo: check uniqueness of executor endpoint
    private void addNewExecutorToPool(Executor newExecutor) {
        this.listOfExecutors.value.add(newExecutor);
        //This is a simple debug message to see that the task list is growing with each new request
        System.out.println("Number of executors: " + listOfExecutors.value.size());
    }

    // subclass for the ExecutorPool class, which provides automatically a Getter and Setter method.
    @Value
    public static class ListOfExecutors {
        private List<Executor> value;
    }

    // subclass for the ExecutorPool class, which provides automatically a Getter and Setter method.
    @Value
    public static class TaskTypeExecutorTypeMapper {
        private HashMap<String, Executor.ExecutorType> value;
    }
}
