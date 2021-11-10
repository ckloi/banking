package com.banking.service.impl;

import com.banking.dao.AccountDao;
import com.banking.dao.TransferHistoryDao;
import com.banking.model.TransferHistory;
import com.banking.service.TransferHistoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/** TransferHistory Service implementation for history creation and retrieval */
@Service
public class TransferHistoryServiceImpl implements TransferHistoryService {

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TransferHistoryDao transferHistoryDao;

  @Override
  public List<TransferHistory> getHistory(Long accountId) {
    try {
      // first checks if the account exists before retrieving history
      accountDao.getAccount(accountId);
      return transferHistoryDao.getHistory(accountId);
    } catch (DataAccessException ex) {
      throw ex;
    }
  }

  @Override
  public Long createHistory(Long senderId, Long recipientId, Double amount) {
    return transferHistoryDao.createHistory(senderId, recipientId, amount);
  }
}
