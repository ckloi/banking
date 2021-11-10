package com.banking.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.banking.controller.TransferHistoryController;
import com.banking.model.TransferHistory;
import com.banking.service.TransferHistoryService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

/** Unit Test for Transfer History Retrieval */
@ExtendWith(MockitoExtension.class)
public class TransferHistoryUnitTest {

  @InjectMocks
  private TransferHistoryController transferHistoryController = new TransferHistoryController();

  @Mock
  private TransferHistoryService transferHistoryService;

  /** Test Normal transfer history with valid accountId */
  @Test
  void transferHistory() {

    TransferHistory transferHistory1 = new TransferHistory(1L, 10001L, 10002L, 100.0, 10000L);
    TransferHistory transferHistory2 = new TransferHistory(2L, 10001L, 10003L, 200.0, 20000L);

    List<TransferHistory> transferHistories = Arrays.asList(transferHistory1, transferHistory2);

    when(transferHistoryService.getHistory(10001L)).thenReturn(transferHistories);

    ResponseEntity<Object> response = transferHistoryController.getTransferHistory(10001L);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(transferHistories, response.getBody());
  }

  /** Test transfer history with non-exist account */
  @Test
  void transferHistoryAccountNotExist() {

    when(transferHistoryService.getHistory(10001L))
        .thenThrow(new DataAccessException("Account Not Found") {});

    Exception exception =
        assertThrows(
            DataAccessException.class,
            () -> {
              transferHistoryController.getTransferHistory(10001L);
            });
    assertEquals("Account Not Found", exception.getMessage());
  }
}
