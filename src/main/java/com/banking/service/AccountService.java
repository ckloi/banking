package com.banking.service;

import com.banking.model.Account;

/** Account Service Interface for account creation, account retrieval and money transfer */
public interface AccountService {

  /**
   * Create Account using customerId and deposit amount
   *
   * @param customerId the ID of the customer
   * @param amount the deposit amount of the customer
   * @return the new account ID created by the service
   */
  public Long createAccount(Long customerId, Double amount);

  /**
   * Get Account from accountId
   *
   * @param accountId the accountId from the request
   * @return the Account object retreived from the service
   */
  public Account getAccount(Long accountId);

  /**
   * Money transfer between sender and recipient with transfer amount Note: transfer opertation may
   * invole mutiple sql quries and they must be in the same transaction
   *
   * @param senderId the account ID of the sender
   * @param recipientId the account ID of the recipient
   * @param amount the transfer amount of the sender
   * @return the ID of transfer history
   */
  public Long transfer(Long senderId, Long recipientId, Double amount);
}
