package com.banking.controller;

import com.banking.model.TransferHistory;
import com.banking.service.TransferHistoryService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Api endpoints for Transfer history creation and retrieval */
@RestController
@RequestMapping("/api/transfer_history")
public class TransferHistoryController {

  @Autowired
  private TransferHistoryService accountService;

  /**
   * Get transfer history from bank account in descending order based on timestamp
   *
   * <p>GET /api/transfer_history/{account_id}
   *
   * @param accountId the account ID
   * @return JSON array object contains transfer history in descending order based on timestamp eg:
   *     [[ { "id": 3, "senderId": 10001, "recipientId": 10002, "amount": 142.0, "timeStampSecond":
   *     1635186221 }, { "id": 2, "senderId": 10001, "recipientId": 10003, "amount": 142.0,
   *     "timeStampSecond": 1635186217 } ]
   * @throws DataAccessException if database error such as account not found
   */
  @GetMapping("/{account_id}")
  public ResponseEntity<Object> getTransferHistory(@PathVariable("account_id") Long accountId) {

    List<TransferHistory> transferHistories = accountService.getHistory(accountId);

    Collections.sort(
        transferHistories,
        new Comparator<TransferHistory>() {
          @Override
          public int compare(TransferHistory lhs, TransferHistory rhs) {
            return Long.signum(rhs.getTimeStampSecond() - lhs.getTimeStampSecond());
          }
        });

    return new ResponseEntity<Object>(transferHistories, HttpStatus.OK);
  }
}
