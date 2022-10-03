package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**This is our aggregate root**/
public class ExecutorPool {

    @Getter
    private final ExecutorPoolName executorPoolName;

    @Getter
    private final ListOfExecutors listOfExecutors;

    //--> using the Singleton pattern here to make lives easy; we will later load it from a repo
    private static final ExecutorPool executorPool = new ExecutorPool(new ExecutorPoolName("tapas-executorpool-group3"));

    private ExecutorPool(ExecutorPoolName executorpoolname) {
        this.executorPoolName = executorpoolname;
        this.listOfExecutors = new ListOfExecutors(new LinkedList<Executor>());
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
}
