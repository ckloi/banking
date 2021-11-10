package com.banking.integration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.controller.TransferHistoryController;
import com.banking.exception.ExceptionHandlingControllerAdvice;
import com.banking.model.TransferHistory;
import com.banking.service.TransferHistoryService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/** Integration Test for Transfer History Retrieval */
@ContextConfiguration(classes = {TransferHistoryController.class})
@WebMvcTest
public class TransferHistoryIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransferHistoryService transferHistoryService;

  @MockBean
  private ExceptionHandlingControllerAdvice exceptionHandlingControllerAdvice;

  /**
   * Test Normal transfer history with valid accountId
   *
   * @throws Exception if mockMvc errors
   */
  @Test
  void transferHistory() throws Exception {

    TransferHistory transferHistory1 = new TransferHistory(1L, 10001L, 10002L, 100.0, 10000L);
    TransferHistory transferHistory2 = new TransferHistory(2L, 10001L, 10003L, 200.0, 20000L);

    List<TransferHistory> transferHistories = Arrays.asList(transferHistory1, transferHistory2);

    when(transferHistoryService.getHistory(10001L)).thenReturn(transferHistories);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/transfer_history/" + 10001L))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(200.0))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value(100.0));

    verify(transferHistoryService).getHistory(10001L);
  }

  /**
   * Test transfer history with non-exist account
   *
   * @throws Exception if mockMvc errors
   */
  @Test
  void transferHistoryAccountNotExist() throws Exception {
    when(transferHistoryService.getHistory(10001L))
        .thenThrow(new DataAccessException("Account Not Found") {});

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/transfer_history/" + 10001L))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());

    verify(transferHistoryService).getHistory(10001L);
  }
}
