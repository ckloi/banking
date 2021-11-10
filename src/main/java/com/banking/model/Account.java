package com.banking.model;

/** Simple bank Account with account number as ID and account balance */
public class Account {

  /** Bank account ID */
  private Long id;

  /** Bank account balance, must have initial deposit */
  private Double balance;

  /**
   * Constructs a Bank account containing the account number as ID and account balance
   *
   * @param id bank account number
   * @param balance bank account balance
   */
  public Account(Long id, Double balance) {
    this.id = id;
    this.balance = balance;
  }

  /**
   * Returns the account number as ID
   *
   * @return the account number as ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the account balance
   *
   * @return the account balance
   */
  public Double getBalance() {
    return balance;
  }

  /** Sets the account number as ID */
  public void setId(Long id) {
    this.id = id;
  }

  /** Sets the account balance */
  public void setBalance(Double balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return String.format("Account [id=%s, balance=%s]", id, balance);
  }
}
