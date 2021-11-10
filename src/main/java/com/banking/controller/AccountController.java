package com.banking.controller;

import com.banking.model.Account;
import com.banking.model.form.CreateAccountForm;
import com.banking.model.form.TransferForm;
import com.banking.service.AccountService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/** Api endpoints for Account creation, balance retrieval and money transfer */
@RestController
@RequestMapping("/api/account")
public class AccountController {

  @Autowired
  private AccountService accountService;

  /**
   * Create bank account
   *
   * <p>POST /api/account/create
   *
   * <p>http body: { customerId:long, amount:double }
   *
   * @param createAccountForm form input contains customerId and deposit amount from the customer to
   *     create bank account
   * @return JSON object contains accountId eg: { accountId: 10001 }
   * @throws DataAccessException if database error such as customer not found
   * @throws IllegalArgumentException if deposit amount is zero or negative
   */
  @RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json")
  public ResponseEntity<Object> createAccount(
      @Valid @RequestBody CreateAccountForm createAccountForm) {

    if (createAccountForm.getAmount() <= 0) {
      throw new IllegalArgumentException("Deposit Amount is not valid");
    }

    Long accountId =
        accountService.createAccount(
            createAccountForm.getcustomerId(), createAccountForm.getAmount());

    Map<String, Long> accountIdMap = new HashMap<String, Long>();
    accountIdMap.put("accountId", accountId);

    return new ResponseEntity<Object>(accountIdMap, HttpStatus.OK);
  }

  /**
   * Get balance from the bank account
   *
   * <p>GET /api/account/balance/{id}
   *
   * @param accountId the account ID
   * @return JSON object contains balance eg: { balance: 100.0 }
   * @throws DataAccessException if database error such as account not found
   */
  @GetMapping("/balance/{id}")
  public ResponseEntity<Object> getAccountBalance(@PathVariable("id") Long accountId) {

    Account account = accountService.getAccount(accountId);

    Map<String, Double> balancedMap = new HashMap<String, Double>();
    balancedMap.put("balance", account.getBalance());

    return new ResponseEntity<Object>(balancedMap, HttpStatus.OK);
  }

  /**
   * Transfer money between bank accounts
   *
   * <p>Create bank account
   *
   * <p>POST /api/account/transfer
   *
   * <p>http body: { senderId:long, recipientId:long, amount:double }
   *
   * @param transferForm form input contains senderId, recipientId and deposit amount from the
   *     customer to send money between two bank accounts
   * @return JSON object contains transferHistoryId eg: { transferHistoryId: 1 }
   * @throws DataAccessException if database error such as sender/recipient account not found
   * @throws IllegalArgumentException if transfer amount is zero or negative
   */
  @RequestMapping(path = "/transfer", method = RequestMethod.POST, consumes = "application/json")
  public ResponseEntity<Object> transfer(@Valid @RequestBody TransferForm transferForm) {

    if (transferForm.getSenderId().equals(transferForm.getRecipientId())) {
      throw new IllegalArgumentException("Cannot transfer between same account");
    }

    if (transferForm.getAmount() <= 0) {
      throw new IllegalArgumentException("Transfer Amount is not valid");
    }

    Long transferHistoryId =
        accountService.transfer(
            transferForm.getSenderId(), transferForm.getRecipientId(), transferForm.getAmount());

    Map<String, Long> transferHistoryMap = new HashMap<String, Long>();
    transferHistoryMap.put("transferHistoryId", transferHistoryId);

    return new ResponseEntity<Object>(transferHistoryMap, HttpStatus.OK);
  }
}
