package com.tt.Service;

import com.tt.Conn;
import com.tt.Repository.Repository;
import com.tt.entity.Certificate;
import com.tt.entity.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CertificateService implements Repository<Certificate, Integer> {


    private Connection connection;

    public CertificateService() {
        connection = new Conn().getConn();
    }


    public void createTableIfNotExists() {
        String query = "CREATE TABLE IF NOT EXISTS certificate ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "studentId INT NOT NULL,"
                + "title VARCHAR(255) NOT NULL,"
                + "issuer VARCHAR(255) NOT NULL,"
                + "issueDate DATE NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Integer add(Certificate item) {
        createTableIfNotExists();
        String query = "INSERT INTO certificate (studentId, title, issuer, issueDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, item.getStudentId());
            preparedStatement.setString(2, item.getTitle());
            preparedStatement.setString(3, item.getIssuer());
            preparedStatement.setDate(4, new java.sql.Date(item.getIssueDate().getTime()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating certificate failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating certificate failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Certificate> readAll() {
        createTableIfNotExists();
        List<Certificate> certificates = new ArrayList<>();
        String query = "SELECT * FROM certificate";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Certificate certificate = new Certificate();
                certificate.setId(resultSet.getInt("id"));
                certificate.setStudentId(resultSet.getInt("studentId"));
                certificate.setTitle(resultSet.getString("title"));
                certificate.setIssuer(resultSet.getString("issuer"));
                certificate.setIssueDate(resultSet.getDate("issueDate"));
                certificates.add(certificate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certificates;
    }

    @Override
    public Certificate read(Integer id) {
        createTableIfNotExists();
        Certificate certificate = null;
        String query = "SELECT * FROM certificate WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    certificate = new Certificate();
                    certificate.setId(resultSet.getInt("id"));
                    certificate.setStudentId(resultSet.getInt("studentId"));
                    certificate.setTitle(resultSet.getString("title"));
                    certificate.setIssuer(resultSet.getString("issuer"));
                    certificate.setIssueDate(resultSet.getDate("issueDate"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certificate;
    }

    public List<Certificate> readByStudentID(Integer studentId){
        createTableIfNotExists();
        List<Certificate> certificates = new ArrayList<>();
        String query = "SELECT * FROM certificate WHERE studentId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Certificate certificate = new Certificate();
                    certificate.setId(resultSet.getInt("id"));
                    certificate.setStudentId(resultSet.getInt("studentId"));
                    certificate.setTitle(resultSet.getString("title"));
                    certificate.setIssuer(resultSet.getString("issuer"));
                    certificate.setIssueDate(resultSet.getDate("issueDate"));
                    certificates.add(certificate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certificates;
    }

    @Override
    public boolean update(Certificate item) {
        createTableIfNotExists();
        String query = "UPDATE certificate SET studentId = ?, title = ?, issuer = ?, issueDate = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, item.getStudentId());
            preparedStatement.setString(2, item.getTitle());
            preparedStatement.setString(3, item.getIssuer());
            preparedStatement.setDate(4, new java.sql.Date(item.getIssueDate().getTime()));
            preparedStatement.setInt(5, item.getId());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        createTableIfNotExists();
        String query = "DELETE FROM certificate WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void ExportCsv(Integer id){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("List Certificate of Student");

            XSSFRow row = null;
            Cell cell = null;
            row = spreadsheet.createRow((short) 2);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("List Certificate of Student");

            row = spreadsheet.createRow((short) 3);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("ID");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Student ID");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Title");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Issuer");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Issue Date");

            List<Certificate> listItem = readByStudentID(id);
            for (int i = 0; i < listItem.size(); i++) {
                Certificate certificate = listItem.get(i);
                row = spreadsheet.createRow((short) 4 + i);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(certificate.getId());
                row.createCell(1).setCellValue(certificate.getStudentId());
                row.createCell(2).setCellValue(certificate.getTitle());
                row.createCell(3).setCellValue(certificate.getIssuer());
                row.createCell(4).setCellValue(certificate.getIssueDate());
            }

            FileOutputStream out = new FileOutputStream(new File("E:/certificate.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void importExel(Integer id){
        File file = new File("");
        String currentDirectory = file.getAbsolutePath();
        String uploadPath = currentDirectory + "/src/main/resources/ImportCsv";
        String csvFilePath = uploadPath + File.separator + "importCertificate.xlsx";

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(csvFilePath)));
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rowsCount = sheet.getLastRowNum();

            // Bắt đầu đọc từ dòng thứ 4 vì dòng 1 và 2 là tiêu đề
            for (int i = 3; i <= rowsCount; i++) {
                XSSFRow row = sheet.getRow(i);

                if (row != null) {
                    Certificate certificate = new Certificate();

                    certificate.setStudentId(id);
                    certificate.setId((int) row.getCell(0).getNumericCellValue());
                    certificate.setTitle(row.getCell(1).getStringCellValue());
                    certificate.setIssuer(row.getCell(2).getStringCellValue());

                    certificate.setIssueDate(row.getCell(3).getDateCellValue());

                    add(certificate);
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
