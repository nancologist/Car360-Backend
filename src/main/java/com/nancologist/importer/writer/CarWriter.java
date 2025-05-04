package com.nancologist.importer.writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nancologist.my_rest_app.model.Car;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class CarWriter {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/springular?currentSchema=car360";
    private static final String FILE_PATH = "src/main/resources/data/result/f11-hr91-lci.json";

    public static void main(String[] args) throws IOException {
        List<Car> cars = getCars();
        try {
            // TODO: Put the username and password into env. vars. and create env vars template and ignore the env var file
            Connection connection = DriverManager.getConnection(JDBC_URL, "admin", "admin");

            String checkQuery = "SELECT COUNT(*) FROM cars";
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
            String insertQuery = """
                    	INSERT INTO car360.cars(
                                color,
                                displacement_in_liter,
                                doors_count,
                                drive,
                                equipment_codes,
                                manufacturer,
                                model,
                                power_in_kw,
                                production_date,
                                transmission,
                                upholstery,
                                vin
                            )
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (Car car : cars) {
                    preparedStatement.setString(1, car.getColor());
                    preparedStatement.setString(2, String.valueOf(car.getDisplacementInLiter()));
                    preparedStatement.setString(3, car.getDoorsCount());
                    preparedStatement.setString(4, car.getDrive());
                    preparedStatement.setArray(5, connection.createArrayOf("VARCHAR", car.getEquipmentCodes().toArray()));
                    preparedStatement.setString(6, car.getManufacturer());
                    preparedStatement.setString(7, car.getModel());
                    preparedStatement.setInt(8, car.getPowerInKw());
                    preparedStatement.setDate(9, new java.sql.Date(car.getProductionDate().getTime()));
                    preparedStatement.setString(10, car.getTransmission());
                    preparedStatement.setString(11, car.getUpholstery());
                    preparedStatement.setString(12, car.getVin());
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

    private static List<Car> getCars() throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Car>>() {}.getType();
            return gson.fromJson(reader, listType);
        }
    }
}
