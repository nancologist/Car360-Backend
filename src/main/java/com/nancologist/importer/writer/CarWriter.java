package com.nancologist.importer.writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nancologist.importer.CarImportModel;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class CarWriter {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/springular?currentSchema=car360";
    private static final String FILE_PATH = "src/main/resources/data/result/car-data-ref.json";

    public static void main(String[] args) throws IOException {
        List<CarImportModel> cars = getCars();
        try {
            // TODO: Put the username and password into env. vars.
            //  and create env vars template and ignore the env var file
            Connection connection = DriverManager.getConnection(JDBC_URL, "admin", "admin");

            connection.setAutoCommit(false);
            String insertQuery = """
                        INSERT INTO car360.cars(
                            color_code,
                            displacement_in_liter,
                            doors_count,
                            drive,
                            equipment_codes,
                            manufacturer,
                            model,
                            power_in_kw,
                            production_date,
                            transmission,
                            upholstery_code,
                            vin,
                            body_style_code,
                            facelift,
                            steering
                            )
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (CarImportModel car : cars) {
                    preparedStatement.setString(1, car.getColorCode());
                    preparedStatement.setFloat(2, car.getDisplacementInLiter());
                    preparedStatement.setInt(3, car.getDoorsCount());
                    preparedStatement.setString(4, car.getDrive());
                    preparedStatement.setArray(
                            5,
                            connection.createArrayOf("VARCHAR", car.getEquipmentCodes().toArray())
                    );
                    preparedStatement.setString(6, car.getManufacturer());
                    preparedStatement.setString(7, car.getModel());
                    preparedStatement.setInt(8, car.getPowerInKw());
                    preparedStatement.setDate(9, new Date(car.getProductionDate().getTime()));
                    preparedStatement.setString(10, car.getTransmission());
                    preparedStatement.setString(11, car.getUpholsteryCode());
                    preparedStatement.setString(12, car.getVin());
                    preparedStatement.setString(13, car.getBodyStyleCode());
                    preparedStatement.setBoolean(14, car.isFacelift());
                    preparedStatement.setString(15, car.getSteering());
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

    private static List<CarImportModel> getCars() throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<CarImportModel>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        }
    }
}
