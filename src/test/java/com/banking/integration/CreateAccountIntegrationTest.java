package com.banking.integration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.controller.AccountController;
import com.banking.exception.ExceptionHandlingControllerAdvice;
import com.banking.model.form.CreateAccountForm;
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

/** Integration Test for Account Creation */
@ContextConfiguration(classes = {AccountController.class})
@WebMvcTest
public class CreateAccountIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @MockBean
  private ExceptionHandlingControllerAdvice exceptionHandlingControllerAdvice;

  /**
   * Test Normal Account Creation with valid customId and deposit amount
   *
   * @throws Exception if mockMVC errors
   */
  @Test
  void createAccount() throws Exception {
    when(accountService.createAccount(1L, 100.0)).thenReturn(10001L);

    CreateAccountForm createAccountForm = new CreateAccountForm(1L, 100.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createAccountForm)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(10001L));
    verify(accountService).createAccount(1L, 100.0);
  }

  /**
   * Test Invalid Account Creation with invalid customId
   *
   * @throws Exception if mockMVC errors
   */
  @Test
  void createAccountCustomerNotFound() throws Exception {
    when(accountService.createAccount(1L, 100.0))
        .thenThrow(new DataAccessException("Customer Not Found") {});

    CreateAccountForm createAccountForm = new CreateAccountForm(1L, 100.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createAccountForm)))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());

    verify(accountService).createAccount(1L, 100.0);
  }

  /**
   * Test Invalid Account Creation with 0 dollar
   *
   * @throws Exception if mockMVC errors
   */
  @Test
  void createAccountZeroAmount() throws Exception {

    CreateAccountForm createAccountForm = new CreateAccountForm(1L, 0.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createAccountForm)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  /**
   * Test Invalid Account Creation with negative dollar
   *
   * @throws Exception if mockMVC errors
   */
  @Test
  void createAccountNegativeAmount() throws Exception {

    CreateAccountForm createAccountForm = new CreateAccountForm(1L, -100.0);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createAccountForm)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
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
