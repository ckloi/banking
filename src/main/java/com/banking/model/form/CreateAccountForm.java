package com.banking.model.form;

import javax.validation.constraints.NotNull;

/** Form input from the customer to create bank account */
public class CreateAccountForm {

  /** Customer ID from user form input, must not be null */
  @NotNull
  private Long customerId;

  /** Deposit amount from user form input, must not be null */
  @NotNull
  private Double amount;

  /**
   * Constructs a CreateAccountForm from customerId and deposit amount
   *
   * @param customerId the ID of customer
   * @param amount the amount to deposit
   */
  public CreateAccountForm(Long customerId, Double amount) {
    this.customerId = customerId;
    this.amount = amount;
  }

  /**
   * Returns the ID of the customer
   *
   * @return the ID of customer
   */
  public Long getcustomerId() {
    return customerId;
  }

  /**
   * Returns deposit amount
   *
   * @return the amount to deposit
   */
  public Double getAmount() {
    return amount;
  }

  /**
   * Sets the ID of the customer
   *
   * @param customerId
   */
  public void setcustomerId(Long customerId) {
    this.customerId = customerId;
  }

  /**
   * Sets the deposit amount
   *
   * @param amount the amount to deposit
   */
  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
