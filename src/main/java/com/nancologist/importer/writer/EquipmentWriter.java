package com.nancologist.importer.writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * DTO class only to fulfill the Gson requirement to import from the JSON file.
 */
class BmwEquipment {
    String code = "";
    String description = "";

    @Override
    public String toString() {
        return "BmwEquipment{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

public class EquipmentWriter {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/springular?currentSchema=car360";
    private static final String FILE_PATH = "src/main/resources/data/result/equipments-lookup.json";

    public static void main(String[] args) throws IOException {
        List<BmwEquipment> equipments = getEquipments();
        try {
            // TODO: Put the username and password into env. vars. and create env vars template and ignore the env var file
            Connection connection = DriverManager.getConnection(JDBC_URL, "admin", "admin");

            String checkQuery = "SELECT COUNT(*) FROM equipments";
            ResultSet resultSet = connection.createStatement().executeQuery(checkQuery);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    System.out.println("Table already contains some data COUNT=" + count);
                    System.out.println("Import aborted.");
                    return;
                }
            }

            connection.setAutoCommit(false);
            String insertQuery = "INSERT INTO equipments (code, description, manufacturer) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (BmwEquipment equipment : equipments) {
                    preparedStatement.setString(1, equipment.code);
                    preparedStatement.setString(2, equipment.description);
                    preparedStatement.setString(3, "BMW");
                    preparedStatement.addBatch();
                }
                int[] affectedRows = preparedStatement.executeBatch();
                System.out.println("Inserted " + Arrays.stream(affectedRows).sum() + " rows.");
                connection.commit();
                System.out.println("Data import completed successfully.");
            } finally {
                connection.setAutoCommit(true);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<BmwEquipment> getEquipments() throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<BmwEquipment>>() {}.getType();
            return gson.fromJson(reader, listType);
        }
    }
}
