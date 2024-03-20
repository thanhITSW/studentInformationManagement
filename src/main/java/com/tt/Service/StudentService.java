package com.tt.Service;

import com.tt.Conn;
import com.tt.Repository.Repository;
import com.tt.entity.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements Repository<Student, Integer> {

    private Connection connection;

    public StudentService() {
        connection = new Conn().getConn();
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "dOB VARCHAR(255)," +
                "gender VARCHAR(10)," +
                "phoneNumber VARCHAR(15)," +
                "address VARCHAR(255)" +
                ")";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer add(Student item) {
        createTableIfNotExists();
        String sql = "INSERT INTO students (name, dOB, gender, phoneNumber, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDOB());
            statement.setString(3, item.getGender());
            statement.setString(4, item.getPhoneNumber());
            statement.setString(5, item.getAddress());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating student failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating student failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> readAll() {
        createTableIfNotExists();
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setDOB(resultSet.getString("dOB"));
                student.setGender(resultSet.getString("gender"));
                student.setPhoneNumber(resultSet.getString("phoneNumber"));
                student.setAddress(resultSet.getString("address"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public Student read(Integer id) {
        createTableIfNotExists();
        String sql = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = new Student();
                    student.setId(resultSet.getInt("id"));
                    student.setName(resultSet.getString("name"));
                    student.setDOB(resultSet.getString("dOB"));
                    student.setGender(resultSet.getString("gender"));
                    student.setPhoneNumber(resultSet.getString("phoneNumber"));
                    student.setAddress(resultSet.getString("address"));
                    return student;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(Student item) {
        createTableIfNotExists();
        String sql = "UPDATE students SET name=?, dOB=?, gender=?, phoneNumber=?, address=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDOB());
            statement.setString(3, item.getGender());
            statement.setString(4, item.getPhoneNumber());
            statement.setString(5, item.getAddress());
            statement.setInt(6, item.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        createTableIfNotExists();
        String sql = "DELETE FROM students WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ExportExcel(){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("List Student");

            XSSFRow row = null;
            Cell cell = null;
            row = spreadsheet.createRow((short) 2);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("List of student");

            row = spreadsheet.createRow((short) 3);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("ID");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Name");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Day of birth");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Gender");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Phone Number");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Address");

            List<Student> listItem = readAll();
            for (int i = 0; i < listItem.size(); i++) {
                Student student = listItem.get(i);
                row = spreadsheet.createRow((short) 4 + i);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getName());
                row.createCell(2).setCellValue(student.getDOB());
                row.createCell(3).setCellValue(student.getGender());
                row.createCell(4).setCellValue(student.getPhoneNumber());
                row.createCell(5).setCellValue(student.getAddress());
            }

            FileOutputStream out = new FileOutputStream(new File("E:/student.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importExel(){
        File file = new File("");
        String currentDirectory = file.getAbsolutePath();
        String uploadPath = currentDirectory + "/src/main/resources/ImportCsv";
        String csvFilePath = uploadPath + File.separator + "importStudent.xlsx";

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(csvFilePath)));
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rowsCount = sheet.getLastRowNum();

            // Bắt đầu đọc từ dòng thứ 4 vì dòng 1 và 2 là tiêu đề
            for (int i = 3; i <= rowsCount; i++) {
                XSSFRow row = sheet.getRow(i);

                if (row != null) {
                    Student student = new Student();

                    student.setId((int) row.getCell(0).getNumericCellValue());
                    student.setName(row.getCell(1).getStringCellValue());
                    student.setDOB(row.getCell(2).getStringCellValue());
                    student.setGender(row.getCell(3).getStringCellValue());
                    student.setPhoneNumber(row.getCell(4).getStringCellValue());
                    student.setAddress(row.getCell(5).getStringCellValue());

                    add(student);
                }
            }

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
