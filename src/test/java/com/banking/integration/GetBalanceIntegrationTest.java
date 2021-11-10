package com.banking.integration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.controller.AccountController;
import com.banking.exception.ExceptionHandlingControllerAdvice;
import com.banking.model.Account;
import com.banking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/** Integration Test for Balance Retrieval */
@ContextConfiguration(classes = {AccountController.class})
@WebMvcTest
public class GetBalanceIntegrationTest {

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
  void getBalnce() throws Exception {
    when(accountService.getAccount(10001L)).thenReturn(new Account(10001L, 100.0));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/account/balance/" + 10001))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(100.0));
    verify(accountService).getAccount(10001L);
  }

  /**
   * Test Invalid Balance Retrieval with invalid accountId
   *
   * @throws Exception if mockMvc errors
   */
  @Test
  void getBalanceAccountNotFound() throws Exception {
    when(accountService.getAccount(10001L))
        .thenThrow(new DataAccessException("Account Not Found") {});

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/account/balance/" + 10001))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());

    verify(accountService).getAccount(10001L);
  }
}
