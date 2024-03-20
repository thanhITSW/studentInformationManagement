package com.tt.Service;

import com.tt.Conn;
import com.tt.Repository.Repository;
import com.tt.entity.Account;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccountService implements Repository<Account, Integer> {

    private Connection connection;

    public AccountService() {
        connection = new Conn().getConn();
    }

    public void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS account ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(255),"
                + "email VARCHAR(255),"
                + "username VARCHAR(255),"
                + "password VARCHAR(255),"
                + "age INT,"
                + "phoneNumber VARCHAR(20),"
                + "status VARCHAR(255),"
                + "role VARCHAR(255),"
                + "history VARCHAR(255))";

        try (
            PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL))
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý exception tùy theo yêu cầu
        }
    }
    @Override
    public Integer add(Account item) {

        createTableIfNotExists();

        String insertSQL = "INSERT INTO account (name, email, username, password, age, phoneNumber, status, role, history) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getEmail());
            preparedStatement.setString(3, item.getUsername());
            preparedStatement.setString(4, item.getPassword());
            preparedStatement.setInt(5, item.getAge());
            preparedStatement.setString(6, item.getPhoneNumber());
            preparedStatement.setString(7, item.getStatus());
            preparedStatement.setString(8, item.getRole());
            preparedStatement.setString(9, item.getHistory());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to get the auto-generated key.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Account> readAll() {
        createTableIfNotExists();
        List<Account> accounts = new ArrayList<>();

        String selectAllSQL = "SELECT * FROM account";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("email"));
                account.setUsername(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                account.setAge(resultSet.getInt("age"));
                account.setPhoneNumber(resultSet.getString("phoneNumber"));
                account.setStatus(resultSet.getString("status"));
                account.setRole(resultSet.getString("role"));
                account.setHistory(resultSet.getString("history"));

                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Account read(Integer id) {
        createTableIfNotExists();
        String selectByIdSQL = "SELECT * FROM account WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("email"));
                account.setUsername(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                account.setAge(resultSet.getInt("age"));
                account.setPhoneNumber(resultSet.getString("phoneNumber"));
                account.setStatus(resultSet.getString("status"));
                account.setRole(resultSet.getString("role"));
                account.setHistory(resultSet.getString("history"));

                return account;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean update(Account item) {
        createTableIfNotExists();
        String updateSQL = "UPDATE account SET name=?, email=?, username=?, password=?, age=?, phoneNumber=?, status=?, role=? , history=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getEmail());
            preparedStatement.setString(3, item.getUsername());
            preparedStatement.setString(4, item.getPassword());
            preparedStatement.setInt(5, item.getAge());
            preparedStatement.setString(6, item.getPhoneNumber());
            preparedStatement.setString(7, item.getStatus());
            preparedStatement.setString(8, item.getRole());
            preparedStatement.setString(9, item.getHistory());
            preparedStatement.setInt(10, item.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        createTableIfNotExists();
        String deleteSQL = "DELETE FROM account WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getCurrentDayTime(){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentDate.format(formatter);
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
