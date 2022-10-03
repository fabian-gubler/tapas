package ch.unisg.tapasexecutorpool.executorpool.application.port.out;

public interface NewTaskExecutionEventPort {
    void publishNewTaskExecutionEvent(NewTaskExecutionEvent event);
}
