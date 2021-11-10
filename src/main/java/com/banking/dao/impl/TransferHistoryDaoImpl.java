package com.banking.dao.impl;

import com.banking.dao.TransferHistoryDao;
import com.banking.mapper.TransferHistoryMapper;
import com.banking.model.TransferHistory;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/** Transfer History DAO implementation for history creation and retrieval using JDBC Template */
@Repository
public class TransferHistoryDaoImpl implements TransferHistoryDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final Logger logger = LoggerFactory.getLogger(TransferHistoryDaoImpl.class);

  @Override
  public Long createHistory(Long senderId, Long recipientId, Double amount) {

    logger.info(
        "Inserting History for senderId : {}, recipientId : {}, amount : {}",
        new Object[] {senderId, recipientId, amount});

    String insertSql =
        "INSERT INTO TRANSFER_HISOTRY (sender_id, recipient_id, amount, timestamp_second)"
            + " VALUES(?,?,?,?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {"ID"});
          Instant instant = Instant.now();
          Long timeStampSecond = instant.getEpochSecond();
          ps.setLong(1, senderId);
          ps.setLong(2, recipientId);
          ps.setDouble(3, amount);
          ps.setLong(4, timeStampSecond);
          return ps;
        },
        keyHolder);

    return keyHolder.getKey().longValue();
  }

  @Override
  public List<TransferHistory> getHistory(Long accountId) {
    logger.info("Retrieving History for accountId : {}", accountId);
    return jdbcTemplate.query(
        "SELECT id, sender_id, recipient_id, amount, timestamp_second FROM TRANSFER_HISOTRY WHERE"
            + " sender_id=? or recipient_id=?",
        new TransferHistoryMapper(),
        accountId,
        accountId);
  }
}
