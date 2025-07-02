package com.nancologist.importer.writer;

import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

public class ColorImageWriter {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/springular?currentSchema=car360";
    public static final String IMGS_PATH = "src/main/resources/data/color-images/";

    public static void main(String[] args) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, "admin", "admin");
            String updateQuery = "UPDATE colors SET image = lo_get(?::oid) WHERE code = ?";
            String importQuery = "SELECT lo_import(?)";
            try (PreparedStatement updateQueryPS = connection.prepareStatement(updateQuery);
                 PreparedStatement importQueryPS = connection.prepareStatement(importQuery)
            ) {
                File imgDir = new File(IMGS_PATH);
                int updateCount = 0;
                for (File file: Objects.requireNonNull(imgDir.listFiles())) {
                    if (file.isFile()) {
                        String imagePath = file.getAbsolutePath();
                        importQueryPS.setString(1, imagePath);

                        String colorCode = Arrays.stream(file.getName().split("-")).map(item -> item.split("\\.")[0]).reduce((a, b) -> b).orElseThrow(() -> new IllegalStateException("Color code not found"));

                        ResultSet result = importQueryPS.executeQuery();
                        if (result.next()) {
                            int largeObjectId = result.getInt("lo_import");
                            updateQueryPS.setInt(1, largeObjectId);
                            updateQueryPS.setString(2, colorCode.toUpperCase());
                            updateCount += updateQueryPS.executeUpdate();
                        }
                        String unlinkQuery = "SELECT lo_unlink(?)"; // For clean up the database // Todo: implement this.
                    }
                }
                System.out.println(updateCount + " rows have been updated!");
            }
            connection.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
