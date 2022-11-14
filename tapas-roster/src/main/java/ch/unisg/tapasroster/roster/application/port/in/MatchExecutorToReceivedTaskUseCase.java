package ch.unisg.tapasroster.roster.application.port.in;


/**
 * Interface which informs the command class which return type and parameter is required to use.
 */
public interface MatchExecutorToReceivedTaskUseCase {
    void matchExecutorToReceivedTask(MatchExecutorToReceivedTaskCommand command);
}
