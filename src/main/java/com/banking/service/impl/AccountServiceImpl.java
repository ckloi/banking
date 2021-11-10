package com.banking.service.impl;

import com.banking.dao.AccountDao;
import com.banking.dao.TransferHistoryDao;
import com.banking.model.Account;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Account Service Layer implementation for account creation, account retrieval and money transfer
 */
@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TransferHistoryDao transferHistoryDao;

  @Override
  public Long createAccount(Long customerId, Double amount) {
    return accountDao.createAccount(customerId, amount);
  }

  @Override
  public Account getAccount(Long accountId) {
    return accountDao.getAccount(accountId);
  }

  @Override
  @Transactional
  public Long transfer(Long senderId, Long recipientId, Double amount) {
    Account sender = accountDao.getAccount(senderId);
    Account recipient = accountDao.getAccount(recipientId);

    if (sender.getBalance() < amount) {
      throw new IllegalArgumentException("Sender Insufficient Fund");
    }

    // update sender and recipient account balance
    accountDao.updateBalance(senderId, sender.getBalance() - amount);

    accountDao.updateBalance(recipientId, recipient.getBalance() + amount);

    // create transfer history
    Long transferHistoryId = transferHistoryDao.createHistory(senderId, recipientId, amount);

    return transferHistoryId;
  }
}
