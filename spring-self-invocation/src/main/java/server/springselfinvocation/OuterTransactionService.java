package server.springselfinvocation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
@Service
public class OuterTransactionService {

    private final InnerTransactionService innerTransactionService;

    @Transactional
    public void outer() {
        log.info("call outer");
        logCurrentTransactionName();
        logActualTransactionActive();
        innerTransactionService.inner();
    }

    private void logActualTransactionActive() {
        boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        log.info("actualTransactionActive = {}", actualTransactionActive);
    }

    private void logCurrentTransactionName() {
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("currentTransactionName = {}", currentTransactionName);
    }
}
