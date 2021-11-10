package com.banking.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.banking.controller.AccountController;
import com.banking.model.form.CreateAccountForm;
import com.banking.service.AccountService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

/** Unit Test for Account Creation */
@ExtendWith(MockitoExtension.class)
public class CreateAccountUnitTest {

  @InjectMocks
  private AccountController accountController = new AccountController();

  @Mock
  private AccountService accountService;

  /** Test Normal Account Creation with valid customId and deposit amount */
  @Test
  void createAccount() {
    when(accountService.createAccount(1L, 100.0)).thenReturn(10001L);

    CreateAccountForm createAccountForm = new CreateAccountForm(1L, 100.0);

    Map<String, Long> expectedResultMap = new HashMap<String, Long>();
    expectedResultMap.put("accountId", 10001L);

    ResponseEntity<Object> response = accountController.createAccount(createAccountForm);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(expectedResultMap, response.getBody());
  }

  /** Test Invalid Account Creation with invalid customId */
  @Test
  void createAccountUserNotFound() {
    when(accountService.createAccount(1L, 100.0))
        .thenThrow(new DataAccessException("Customer Not Found") {});

    CreateAccountForm createAccountForm = new CreateAccountForm(1L, 100.0);

    Exception exception =
        assertThrows(
            DataAccessException.class,
            () -> {
              accountController.createAccount(createAccountForm);
            });
    assertEquals("Customer Not Found", exception.getMessage());
  }

  /** Test Invalid Account Creation with 0 dollar */
  @Test
  void createAccountZeroAmount() {
    CreateAccountForm createAccountForm = new CreateAccountForm(1L, 0.0);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountController.createAccount(createAccountForm));
    assertEquals("Deposit Amount is not valid", exception.getMessage());
  }

  /** Test Invalid Account Creation with negative dollar */
  @Test
  void createAccountNegativeAmount() {
    CreateAccountForm createAccountForm = new CreateAccountForm(1L, -100.0);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountController.createAccount(createAccountForm));
    assertEquals("Deposit Amount is not valid", exception.getMessage());
  }
}
