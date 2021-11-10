package com.banking.integration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.controller.AccountController;
import com.banking.exception.ExceptionHandlingControllerAdvice;
import com.banking.model.form.TransferForm;
import com.banking.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/** Integration Test for Money Transfer */
@ContextConfiguration(classes = {AccountController.class})
@WebMvcTest
public class AccountTransferIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @MockBean
  private ExceptionHandlingControllerAdvice exceptionHandlingControllerAdvice;

  /**
   * Test Normal Balance Retrieval with valid accountId
   *
   * @throws Exception if mockMvc errors
   */
  @Test
  void transferBetweenAccounts() throws Exception {
    when(accountService.transfer(10001L, 10002L, 100.0)).thenReturn(1L);

    TransferForm transferForm = new TransferForm(10001L, 10002L, 100.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transferForm)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.transferHistoryId").value(1L));
    verify(accountService).transfer(10001L, 10002L, 100.0);
  }

  /**
   * Test money transfer between accounts with 0 dollar
   *
   * @throws Exception if mockMvc errors
   */
  @Test
  void transferBetweenAccountsZeroAmount() throws Exception {

    TransferForm transferForm = new TransferForm(10001L, 10002L, 0.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transferForm)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  /**
   * Test money transfer between accounts with negative dollar
   *
   * @throws Exception if mockMvc errors
   */
  @Test
  void transferBetweenAccountsNegativeAmount() throws Exception {

    TransferForm transferForm = new TransferForm(10001L, 10002L, -100.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transferForm)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  /**
   * Test money transfer with non-exist sender or recipient
   *
   * @throws Exception if mockMvc errors
   */
  @Test
  void transferBetweenAccountNotExist() throws Exception {
    when(accountService.transfer(10001L, 10002L, 100.0))
        .thenThrow(new DataAccessException("Sender/Recipient not exist") {});

    TransferForm transferForm = new TransferForm(10001L, 10002L, 100.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transferForm)))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    verify(accountService).transfer(10001L, 10002L, 100.0);
  }

  /**
   * Test money transfer between accounts when sender doesn't have sufficient fund
   *
   * @throws Exception if mockMvc errors
   */
  @Test
  void TransferBetweenAccountsInsufficientFund() throws Exception {
    when(accountService.transfer(10001L, 10002L, 100.0))
        .thenThrow(new IllegalArgumentException("Sender Insufficient Fund"));
    TransferForm transferForm = new TransferForm(10001L, 10002L, 100.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transferForm)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    verify(accountService).transfer(10001L, 10002L, 100.0);
  }

  /**
   * Helper function to map object to json string
   *
   * @param obj the object to pass in http request body
   * @return json string of the object
   */
  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
