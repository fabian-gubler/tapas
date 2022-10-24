package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.out.LoadExecutorPort;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveExecutorFromExecutorpoolQuery;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveExecutorFromExecutorpoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorNotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
@Service("RetrieveExecutorByType")
public class RetrieveExecutorByTypeService implements RetrieveExecutorFromExecutorpoolUseCase {

    private final LoadExecutorPort loadExecutorPort;

    @Override
    public Executor retrieveExecutorFromExecutorPool(RetrieveExecutorFromExecutorpoolQuery query) {
        try {
            Executor executorList = loadExecutorPort.loadExecutorByType(query.getExecutorType().getValue());

            return executorList;
        } catch (ExecutorNotFoundError e) {

            return null;
        }
    }
}
