package com.nancologist.importer.data_transformer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class ColorImportObject {
    String manufacturer;
    String code;
    String name;

    public ColorImportObject(String manufacturer, String code, String name) {
        this.manufacturer = manufacturer;
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nColorImportObject{" +
                "manufacturer='" + manufacturer + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}


public class ColorDataTransformer {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/springular?currentSchema=car360";
    static final private Path FILE_PATH = Paths.get("src/main/resources/data/orig/550iAT_F11_LCI_HR91.txt");
    static final private Pattern colorPattern = Pattern.compile("(Colour|Farbe|Färg)\\s+(.+)\\((\\w{2,})\\)(?:\\s|$)");

    public static void main(String[] args) throws IOException {
        List<ColorImportObject> colors = readFile();
        writeToDb(colors);
    }

    private static List<ColorImportObject> readFile() throws IOException {
        try (Stream<String> lines = Files.lines(FILE_PATH)) {
            List<ColorImportObject> colors = new ArrayList<>();

            lines.forEach( (line) -> {
                Matcher colorMatcher = colorPattern.matcher(line);
                if (colorMatcher.find()) {
                    String colorCode = colorMatcher.group(3).trim();
                    boolean codeAlreadyExists = colors.stream().anyMatch(item -> item.code.equals(colorCode));
                    if (!codeAlreadyExists) {
                        ColorImportObject color = new ColorImportObject("BMW", colorCode, colorMatcher.group(2).trim());
                        colors.add(color);
                    }
                }
            });
            return colors;
        }
    }

    private static void writeToDb(List<ColorImportObject> colors) {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, "admin", "admin");
            connection.setAutoCommit(false);
            String insertQuery = "INSERT INTO colors (manufacturer, code, name) VALUES (?, ?, ?);";

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                for (ColorImportObject color : colors) {
                    statement.setString(1, color.manufacturer);
                    statement.setString(2, color.code);
                    statement.setString(3, color.name);
                    statement.addBatch();
                }
                int[] affectedRows = statement.executeBatch();
                System.out.println("Inserted " + Arrays.stream(affectedRows).sum() + " rows.");
                connection.commit();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
