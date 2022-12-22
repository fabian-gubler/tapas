package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import ch.unisg.tapasexecutorpool.executorpool.application.port.out.AddExecutorPort;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.LoadExecutorPort;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorNotFoundError;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecutorPersistenceAdapter implements
    AddExecutorPort,
    LoadExecutorPort {

    @Autowired
    private final ExecutorRepository executorRepository;

    @Autowired
    private final ExecutorMapper executorMapper;

    @Override
    public void addExecutor(Executor executor) {
        MongoExecutorDocument mongoExecutorDocument = executorMapper.mapToMongoDocument(executor);
        executorRepository.save(mongoExecutorDocument);
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
    }

    @Override
    public Executor loadExecutor(Executor.ExecutorId executorId) throws ExecutorNotFoundError {
        MongoExecutorDocument mongoExecutorDocument = executorRepository.findByExecutorId(executorId.getValue());

        if(mongoExecutorDocument == null){
            throw new ExecutorNotFoundError();
        }
        return executorMapper.mapToDomainEntity(mongoExecutorDocument);
    }

    @Override
    @Query()
    public Executor loadExecutorByType(String executorType) throws ExecutorNotFoundError {
        MongoExecutorDocument mongoExecutorDocument = executorRepository.findFirstByExecutorType(executorType);

        if(mongoExecutorDocument == null){
            throw new ExecutorNotFoundError();
        }
        Executor executor = executorMapper.mapToDomainEntity(mongoExecutorDocument);

        return executor;
    }
}
