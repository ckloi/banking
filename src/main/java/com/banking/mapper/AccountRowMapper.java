package com.banking.mapper;

import com.banking.model.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/** Row Mapper for Account Object From JDBC ResultSet */
public class AccountRowMapper implements RowMapper<Account> {
  @Override
  public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
    Account account = new Account(rs.getLong("id"), rs.getDouble("balance"));
    return account;
  }
}
