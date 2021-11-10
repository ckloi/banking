package com.banking.model;

/** Transfer History is a record when customer transfer money between accounts */
public class TransferHistory {
  /** Transfer History ID */
  private Long id;

  /** Sender Id who initiates money transfer */
  private Long senderId;

  /** Recipient Id who receive money transfer */
  private Long recipientId;

  /** Transfer amount */
  private Double amount;

  /** Epoch timestamp in second when transfer occurs */
  private Long timeStampSecond;

  /**
   * Constructs a Transfer History containing id, senderId, recipientId, amount,and timeStampSecond
   *
   * @param id te ID of transfer history
   * @param senderId the ID of sender
   * @param recipientId the ID of recipient
   * @param amount amount to transfer
   * @param timeStampSecondtimestamp epoch in second when transfer occurs
   */
  public TransferHistory(
      Long id, Long senderId, Long recipientId, Double amount, Long timeStampSecond) {
    this.id = id;
    this.senderId = senderId;
    this.recipientId = recipientId;
    this.amount = amount;
    this.timeStampSecond = timeStampSecond;
  }

  /**
   * Returns transfer History ID
   *
   * @return transfer History ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns render ID who initiates money transfer
   *
   * @return sender ID who initiates money transfer
   */
  public Long getSenderId() {
    return senderId;
  }

  /**
   * Returns recipient ID who receive money transfer
   *
   * @return recipient ID who receive money transfer
   */
  public Long getRecipientId() {
    return recipientId;
  }

  /**
   * Returns transfer amount
   *
   * @return transfer amount
   */
  public Double getAmount() {
    return amount;
  }

  /**
   * Returns Epoch timestamp in second when transfer occurs
   *
   * @return epoch timestamp in second when transfer occurs
   */
  public Long getTimeStampSecond() {
    return timeStampSecond;
  }

  /**
   * Sets transfer History ID
   *
   * @param id transfer History ID
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Sets sender ID who initiates money transfer
   *
   * @param senderId sender ID who initiates money transfer
   */
  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  /**
   * Sets recipient ID who receive money transfer
   *
   * @param recipientId recipient ID who receive money transfer
   */
  public void setRecipientId(Long recipientId) {
    this.recipientId = recipientId;
  }

  /**
   * Sets transfer amount
   *
   * @param amount transfer amount
   */
  public void setAmount(Double amount) {
    this.amount = amount;
  }

  /**
   * Sets epoch timestamp in second when transfer occurs
   *
   * @param timeStampSecond epoch timestamp in second when transfer occurs
   */
  public void setTimeStampSecond(Long timeStampSecond) {
    this.timeStampSecond = timeStampSecond;
  }

  @Override
  public String toString() {
    return String.format(
        "TransferHistory [id=%s, senderId=%s, recipientId=%s, amount=%s, timeStampSecond=%s]",
        id, senderId, recipientId, amount, timeStampSecond);
  }
}
