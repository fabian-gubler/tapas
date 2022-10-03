package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.*;


/**This is our aggregate root**/
public class ExecutorPool {

    @Getter
    private final ExecutorPoolName executorPoolName;

    @Getter
    private final ListOfExecutors listOfExecutors;

    @Getter
    private final TaskTypeExecutorTypeMapper taskTypeExecutorTypeMapper;

    //--> using the Singleton pattern here to make lives easy; we will later load it from a repo
    private static final ExecutorPool executorPool = new ExecutorPool(new ExecutorPoolName("tapas-executorpool-group3"));

    private ExecutorPool(ExecutorPoolName executorpoolname) {
        this.executorPoolName = executorpoolname;
        this.listOfExecutors = new ListOfExecutors(new LinkedList<Executor>());

        this.taskTypeExecutorTypeMapper = new TaskTypeExecutorTypeMapper(new HashMap<String, Executor.Type>());

        // initialize tasktype to executortype mapper -> initial values are added
        // this takes over the function of the roster for the momennt
        // in a simple hashmap for every tasktype as string there is a matching executor type assigned
        this.taskTypeExecutorTypeMapper.value.put("taskType1", Executor.Type.JOKE);
        this.taskTypeExecutorTypeMapper.value.put("taskType2", Executor.Type.COMPUTE);
    }

    @Value
    public static class ExecutorPoolName {
        private String value;
    }

    public Executor addNewExecutorToExecutorPoolWithTypeAndEndpoint(Executor.Endpoint endpoint, Executor.ExecutorType executorType) {
        Executor newExecutor = Executor.createExecutorWithTypeAndEnpoint(executorType, endpoint);
        this.addNewExecutorToPool(newExecutor);

        return newExecutor;
    }

    public static ExecutorPool getExecutorPool() {
        return executorPool;
    }

    public Boolean removeExecutorFromExecutorPoolById(Executor.ExecutorId executorId) {
        Optional<Executor> executor = this.retrieveExecutorById(executorId);

        if (executor.isPresent()) {
            this.listOfExecutors.getValue().remove(executor.get());
            return true;
        }

        return false;
    }

    public Optional<Executor> retrieveExecutorById(Executor.ExecutorId id) {
        for (Executor executor : listOfExecutors.value) {
            if (executor.getExecutorId().getValue().equalsIgnoreCase(id.getValue())) {
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    public Optional<Executor> findAvailableExecutorFromTaskTypeString(String taskType) {
        for (Executor executor : listOfExecutors.value) {
            if (executor.getExecutorType().getValue() == this.taskTypeExecutorTypeMapper.value.get(taskType)) {
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    // todo: check uniqueness of executor endpoint
    private void addNewExecutorToPool(Executor newExecutor) {
        this.listOfExecutors.value.add(newExecutor);
        //This is a simple debug message to see that the task list is growing with each new request
        System.out.println("Number of executors: " + listOfExecutors.value.size());
    }

    @Value
    public static class ListOfExecutors {
        private List<Executor> value;
    }
    @Value
    public static class TaskTypeExecutorTypeMapper {
        private HashMap<String, Executor.Type> value;
    }
}
