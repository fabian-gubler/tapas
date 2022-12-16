package ch.unisg.tapasexecutorpool.executorpool.application.port.out;

public interface NewExecutorAddedEventPort {

    void publishNewExecutorAddedEvent(NewExecutorAddedEvent event);
}
