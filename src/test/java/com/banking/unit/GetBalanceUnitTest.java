package com.banking.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.banking.controller.AccountController;
import com.banking.model.Account;
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

/** Unit Test for Balance Retrieval */
@ExtendWith(MockitoExtension.class)
public class GetBalanceUnitTest {

  @InjectMocks
  private AccountController accountController = new AccountController();

  @Mock
  private AccountService accountService;

  /** Test Normal Balance Retrieval with valid accountId */
  @Test
  void getBalance() {
    when(accountService.getAccount(10001L)).thenReturn(new Account(10001L, 100.0));

    Map<String, Double> expectedResultMap = new HashMap<String, Double>();
    expectedResultMap.put("balance", 100.0);

    ResponseEntity<Object> response = accountController.getAccountBalance(10001L);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(expectedResultMap, response.getBody());
  }

  /** Test Invalid Balance Retrieval with invalid accountId */
  @Test
  void getBalanceAccountNotFound() {
    when(accountService.getAccount(10001L))
        .thenThrow(new DataAccessException("Account Not Found") {});

    Exception exception =
        assertThrows(
            DataAccessException.class,
            () -> {
              accountController.getAccountBalance(10001L);
            });
    assertEquals("Account Not Found", exception.getMessage());
  }
}
