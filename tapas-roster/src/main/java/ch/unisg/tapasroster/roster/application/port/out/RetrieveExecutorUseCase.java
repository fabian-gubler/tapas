package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.Executor;

public interface RetrieveExecutorUseCase {
    Executor.Endpoint retrieveExecutorUseCase(RetrieveExecutorQuery query);
}
