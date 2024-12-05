package server.springselfinvocation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OuterTransactionServiceTest {

    @Autowired
    private OuterTransactionService outerTransactionService;

    @Test
    void transactionBoundaryTest() {
        outerTransactionService.outer();
    }
}
