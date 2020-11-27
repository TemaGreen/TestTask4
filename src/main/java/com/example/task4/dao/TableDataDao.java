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
                    "SELECT id, date, name, value FROM tabledata ORDER BY date ASC, name ASC",
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

            a.add(new TableData("2019-01-01","Газпром", 7500));
            a.add(new TableData("2019-01-05","Газпром", 8000));
            a.add(new TableData("2019-01-06","Газпром", 6000));
            a.add(new TableData("2019-01-10","Газпром", 10000));
            a.add(new TableData("2019-01-15","Газпром", 10500));
            a.add(new TableData("2019-01-20","Газпром", 9000));
            a.add(new TableData("2019-02-02","Газпром", 10500));
            a.add(new TableData("2019-02-07","Газпром", 9750));
            a.add(new TableData("2019-02-18","Газпром", 8423));
            a.add(new TableData("2019-02-21","Газпром", 6750));

            a.add(new TableData("2019-01-02", "Автоваз", 2500));
            a.add(new TableData("2019-01-03", "Автоваз", 1000));
            a.add(new TableData("2019-01-05", "Автоваз", 3400));
            a.add(new TableData("2019-01-10", "Автоваз", 4200));
            a.add(new TableData("2019-01-18", "Автоваз", 5750));
            a.add(new TableData("2019-02-05", "Автоваз", 6300));
            a.add(new TableData("2019-02-09", "Автоваз", 7350));
            a.add(new TableData("2019-02-12", "Автоваз", 5250));
            a.add(new TableData("2019-02-23", "Автоваз", 4300));
            a.add(new TableData("2019-03-03", "Автоваз", 3304));

            a.add(new TableData("2019-01-04", "Сбербанк", 6000));
            a.add(new TableData("2019-01-09", "Сбербанк", 7500));
            a.add(new TableData("2019-01-10", "Сбербанк", 7300));
            a.add(new TableData("2019-01-20", "Сбербанк", 8200));
            a.add(new TableData("2019-02-02", "Сбербанк", 8750));
            a.add(new TableData("2019-02-10", "Сбербанк", 7000));
            a.add(new TableData("2019-02-15", "Сбербанк", 9360));
            a.add(new TableData("2019-02-18", "Сбербанк", 10050));
            a.add(new TableData("2019-03-01", "Сбербанк", 11245));
            a.add(new TableData("2019-03-10", "Сбербанк", 12345));

            insertAll(a);
            list = finaAll();
        }
        return list;
    }
}