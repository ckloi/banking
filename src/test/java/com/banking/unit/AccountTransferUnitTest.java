package com.banking.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.banking.controller.AccountController;
import com.banking.model.form.TransferForm;
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

/** Unit Test for Money Transfer */
@ExtendWith(MockitoExtension.class)
public class AccountTransferUnitTest {

  @InjectMocks
  private AccountController accountController = new AccountController();

  @Mock
  private AccountService accountService;

  /** Test Normal money transfer with valid accountId and amount */
  @Test
  void transferBetweenAccounts() {
    when(accountService.transfer(10001L, 10002L, 50.5)).thenReturn(1L);

    TransferForm transferForm = new TransferForm(10001L, 10002L, 50.5);

    Map<String, Long> expectedResultMap = new HashMap<String, Long>();
    expectedResultMap.put("transferHistoryId", 1L);

    ResponseEntity<Object> response = accountController.transfer(transferForm);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(expectedResultMap, response.getBody());
  }

  /** Test money transfer between accounts with 0 dollar */
  @Test
  void transferBetweenAccountsZeroAmount() {
    TransferForm transferForm = new TransferForm(10001L, 10002L, 0.0);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class, () -> accountController.transfer(transferForm));
    assertEquals("Transfer Amount is not valid", exception.getMessage());
  }

  /** Test money transfer between accounts with negative dollar */
  @Test
  void transferBetweenAccountsNegativeAmount() {
    TransferForm transferForm = new TransferForm(10001L, 10002L, -100.0);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class, () -> accountController.transfer(transferForm));
    assertEquals("Transfer Amount is not valid", exception.getMessage());
  }

  /** Test money transfer with non-exist sender or recipient */
  @Test
  void transferBetweenAccountNotExist() {
    when(accountService.transfer(10001L, 10002L, 100.0))
        .thenThrow(new DataAccessException("Sender/Recipient not exist") {});

    TransferForm transferForm = new TransferForm(10001L, 10002L, 100.0);

    Exception exception =
        assertThrows(DataAccessException.class, () -> accountController.transfer(transferForm));
    assertEquals("Sender/Recipient not exist", exception.getMessage());
  }

  /** Test money transfer between accounts when sender doesn't have sufficient fund */
  @Test
  void TransferBetweenAccountsInsufficientFund() {

    when(accountService.transfer(10001L, 10002L, 100.0))
        .thenThrow(new IllegalArgumentException("Sender Insufficient Fund"));

    TransferForm transferForm = new TransferForm(10001L, 10002L, 100.0);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class, () -> accountController.transfer(transferForm));
    assertEquals("Sender Insufficient Fund", exception.getMessage());
  }
}
