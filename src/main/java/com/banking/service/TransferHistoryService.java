package com.banking.service;

import com.banking.model.TransferHistory;
import java.util.List;

/** TransferHistory Service Interface for history creation and retrieval */
public interface TransferHistoryService {

  /**
   * Create TransferHistory using senderId, recipientId, and amount
   *
   * @param senderId the account ID of the sender
   * @param recipientId the account ID of the recipient
   * @param amount the transfer amount
   * @return the transfer history ID
   */
  public Long createHistory(Long senderId, Long recipientId, Double amount);

  /**
   * Get TransferHistory using accountId
   *
   * @param accountId the account ID
   * @return a list of transfer history
   */
  public List<TransferHistory> getHistory(Long accountId);
}
