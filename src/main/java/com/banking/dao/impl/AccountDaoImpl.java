package com.banking.dao.impl;

import com.banking.dao.AccountDao;
import com.banking.mapper.AccountRowMapper;
import com.banking.model.Account;
import java.sql.PreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Account DAO implementation for account creation, account retrieval and balance update using JDBC
 * Template
 */
@Repository
public class AccountDaoImpl implements AccountDao {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

  @Override
  public Long createAccount(Long customerId, Double amount) {

    logger.info(
        "Inserting Account, customerId : {}, amount : {}", new Object[] {customerId, amount});

    String insertSql = "INSERT INTO ACCOUNT (customer_id,balance)" + " VALUES(?,?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {"ID"});
          ps.setLong(1, customerId);
          ps.setDouble(2, amount);
          return ps;
        },
        keyHolder);

    return keyHolder.getKey().longValue();
  }

  @Override
  public Account getAccount(Long accountId) {
    logger.info("Retrieving Account, accountId : {}", accountId);
    return jdbcTemplate.queryForObject(
        "SELECT id, balance, customer_id FROM ACCOUNT WHERE id=?",
        new AccountRowMapper(),
        accountId);
  }

  @Override
  public void updateBalance(Long accountId, Double balance) {

    logger.info(
        "Updating Account Balance for accountId : {}, balance : {}",
        new Object[] {accountId, balance});

    jdbcTemplate.update(
        "UPDATE ACCOUNT SET balance=? WHERE id=?", new Object[] {balance, accountId});
  }
}
