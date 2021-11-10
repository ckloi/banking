package com.banking.model.form;

import javax.validation.constraints.NotNull;

/** Form input from the customer to transfer money between bank accounts */
public class TransferForm {

  /** Sender ID from user form input, must not be null */
  @NotNull
  private Long senderId;

  /** Recipient ID from user form input, must not be null */
  @NotNull
  private Long recipientId;

  /** Transfer amount from user form input, must not be null */
  @NotNull
  private Double amount;

  /**
   * Constructs a TransferForm from senderId, recipientId and transfer amount
   *
   * @param senderId the account ID of the sender
   * @param recipientId the account ID of the recipient
   * @param amount the transfer amount of the sender
   */
  public TransferForm(Long senderId, Long recipientId, Double amount) {
    this.senderId = senderId;
    this.recipientId = recipientId;
    this.amount = amount;
  }

  /**
   * Returns the ID of sender
   *
   * @return the ID of sender
   */
  public Long getSenderId() {
    return senderId;
  }

  /**
   * Returns the ID of recipient
   *
   * @return the ID of recipient
   */
  public Long getRecipientId() {
    return recipientId;
  }

  /**
   * Returns the transfer amount
   *
   * @return the transfer amount
   */
  public Double getAmount() {
    return amount;
  }

  /**
   * Sets the ID of sender
   *
   * @param senderId the ID of the sender
   */
  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  /**
   * Sets the ID of recipient
   *
   * @param recipientId the ID of recipient
   */
  public void setRecipientId(Long recipientId) {
    this.recipientId = recipientId;
  }

  /**
   * Sets the transfer amount
   *
   * @param amount the transfer amount
   */
  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
