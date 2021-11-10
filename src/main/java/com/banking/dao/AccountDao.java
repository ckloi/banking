package com.banking.dao;

import com.banking.model.Account;

/** Account DAO Interface for account creation, account retrieval and balance update */
public interface AccountDao {

  /**
   * Create Account from the database
   *
   * @param customerId the ID of customer
   * @param amount the deposit amount
   * @return the accoundId created from the database
   */
  public Long createAccount(Long customerId, Double amount);

  /**
   * Get Account from the database
   *
   * @param accountId the ID of account
   * @return the Account object retreived from the database
   */
  public Account getAccount(Long accountId);

  /**
   * Update Account balance from the database
   *
   * @param accountId the ID of account
   * @param balance the new balance
   */
  public void updateBalance(Long accountId, Double balance);
}
