package com.example.task4.dao;

import com.example.task4.component.ConnectManager;
import com.example.task4.component.Counter;
import com.example.task4.entity.TableData;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Service
public class TableDataDao implements DataAccessObject<TableData> {

    @Override
    public void insert(TableData value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "INSERT INTO tabledata(" +
                            "id," +
                            "date, " +
                            "name, " +
                            "value" +
                            ") VALUES (?,?,?,?);"
            );
            preparedStatement.setInt(1, value.getId());
            preparedStatement.setDate(2, value.getDate());
            preparedStatement.setString(3, value.getName());
            preparedStatement.setLong(4, value.getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    @Override
    public void insertAll(List<TableData> value) {
        try {
            int i = value.size();
            Connection connect = ConnectManager.getConnection();
            String sqlRequest = "INSERT INTO tabledata(id, date, name, value) VALUES";
            while (i > 1) {
                sqlRequest += " (?,?,?,?),";
                i--;
            }
            sqlRequest += " (?,?,?,?);";
            PreparedStatement preparedStatement = connect.prepareStatement(sqlRequest);
            ListIterator<TableData> listIterator = value.listIterator();
            while (listIterator.hasNext()) {
                TableData tableData = listIterator.next();
                preparedStatement.setInt(i++, tableData.getId());
                preparedStatement.setDate(i++, tableData.getDate());
                preparedStatement.setString(i++, tableData.getName());
                preparedStatement.setLong(i++, tableData.getValue());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "DELETE FROM tabledata WHERE id = ?;"
            );
            preparedStatement.setInt(1, id);
            TableData.counter.addFreedId(id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    @Override
    public void deleteAll() {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "DELETE FROM tabledata;"
            );
            TableData.counter.reset();
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    @Override
    public void update(Integer id, TableData value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "UPDATE tabledata SET date = ?, name = ?, value = ? WHERE id = ?;"
            );
            preparedStatement.setDate(1, value.getDate());
            preparedStatement.setString(2, value.getName());
            preparedStatement.setLong(3, value.getValue());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    @Override
    public TableData findId(int id) {
        TableData tableData = null;
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    " SELECT id, date, name, value FROM tabledata WHERE id = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tableData = new TableData(
                    id,
                    resultSet.getDate(1),
                    resultSet.getString(2),
                    resultSet.getLong(3)
            );
        } catch (SQLException ex) {
            ex.getSQLState();
        } finally {
            return tableData;
        }
    }

    @Override
    public List<TableData> finaAll() {
        List<TableData> result = new LinkedList<>();
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "SELECT id, date, name, value FROM tabledata ORDER BY date",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int count = resultSet.getRow();
            resultSet.beforeFirst();
            TableData tableData;
            for (int i = 1; i <= count; i++) {
                resultSet.next();
                tableData = new TableData(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getString(3),
                        resultSet.getLong(4)
                );
                result.add(tableData);
            }
        } catch (SQLException ex) {
            ex.getSQLState();
        } finally {
            return result;
        }
    }

    public List<TableData> init() {
        List<TableData> list = finaAll();
        if (list.isEmpty()) {
            List<TableData> a = new LinkedList<>();
            a.add(new TableData("2019-01-01", "Газпром", 2000));
            a.add(new TableData("2019-01-01", "Автоваз", 2500));
            a.add(new TableData("2019-01-05", "Сбербанк", 10000));
            a.add(new TableData("2019-01-10", "Газпром", 2500));
            a.add(new TableData("2019-01-07", "Автоваз", 2100));
            insertAll(a);
            list = finaAll();
        }
        return list;
    }
}