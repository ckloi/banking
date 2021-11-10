package com.banking.dao;

import com.banking.model.TransferHistory;
import java.util.List;

/** TransferHistory DAO Interface for history creation and retrieval */
public interface TransferHistoryDao {

  /**
   * Create TransferHistory from the database
   *
   * @param senderId the account ID of the sender
   * @param recipientId the account ID of the recipient
   * @param amount the transfer amount
   * @return the transfer history ID
   */
  public Long createHistory(Long senderId, Long recipientId, Double amount);

  /**
   * Get TransferHistory from the database
   *
   * @param accountId the account ID
   * @return a list of transfer history
   */
  public List<TransferHistory> getHistory(Long accountId);
}
