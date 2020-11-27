package com.example.task4.component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class TableDataCounter extends Counter {

    @Override
    public void getFreedId() {
        try {
            Connection connection = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id FROM tabledata order by id;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            firstId = resultSet.getInt(1) + 1;
            int count = resultSet.getRow();
            resultSet.beforeFirst();
            HashSet<Integer> busyId = new HashSet<>();
            for (int i = 0; i < count; i++) {
                resultSet.next();
                busyId.add(resultSet.getInt(1));
            }
            int id = 0;
            while (id < firstId) {
                if (!busyId.contains(id)) {
                    freedId.offer(id);
                }
                id++;
            }
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }
}
