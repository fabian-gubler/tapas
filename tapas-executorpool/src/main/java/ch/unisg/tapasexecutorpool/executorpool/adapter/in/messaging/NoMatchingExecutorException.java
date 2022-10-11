package ch.unisg.tapasexecutorpool.executorpool.adapter.in.messaging;

public class NoMatchingExecutorException extends RuntimeException{

    public NoMatchingExecutorException(String message) {
        super(message);
    }
}
