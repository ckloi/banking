package com.banking.mapper;

import com.banking.model.TransferHistory;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/** Row Mapper for TransferHistory Object From JDBC ResultSet */
public class TransferHistoryMapper implements RowMapper<TransferHistory> {

  @Override
  public TransferHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
    TransferHistory history =
        new TransferHistory(
            rs.getLong("id"),
            rs.getLong("sender_id"),
            rs.getLong("recipient_id"),
            rs.getDouble("amount"),
            rs.getLong("timestamp_second"));
    return history;
  }
}
