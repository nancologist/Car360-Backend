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

class UpholsteryImportObject {
    String manufacturer;
    String code;
    String name;

    public UpholsteryImportObject(String manufacturer, String code, String name) {
        this.manufacturer = manufacturer;
        this.code = code;
        this.name = name;
    }
}

public class UpholsteryDataTransformer {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/springular?currentSchema=car360";
    static final private Path FILE_PATH = Paths.get("src/main/resources/data/orig/550iAT_F11_LCI_HR91.txt");
    static final private Pattern upholsteryPattern = Pattern.compile("(Upholstery|Polsterung|Stoldynor)\\s+(.+)\\((\\w+)\\)");

    public static void main(String[] args) throws IOException {
        List<UpholsteryImportObject> upholsteries = readFile();
        writeToDb(upholsteries);
    }

    private static List<UpholsteryImportObject> readFile() throws IOException {
        try (Stream<String> lines = Files.lines(FILE_PATH)) {
            List<UpholsteryImportObject> upholsteries = new ArrayList<>();

            lines.forEach( (line) -> {
                Matcher upholsteryMatcher = upholsteryPattern.matcher(line);
                if (upholsteryMatcher.find()) {
                    String upholsteryCode = upholsteryMatcher.group(3).trim();
                    boolean codeAlreadyExists = upholsteries.stream().anyMatch(item -> item.code.equals(upholsteryCode));
                    if (!codeAlreadyExists) {
                        UpholsteryImportObject upholstery = new UpholsteryImportObject(
                                "BMW",
                                upholsteryCode,
                                upholsteryMatcher.group(2).trim()
                        );
                        upholsteries.add(upholstery);
                    }
                }
            });
            return upholsteries;
        }
    }

    private static void writeToDb(List<UpholsteryImportObject> upholsteries) {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, "admin", "admin");
            connection.setAutoCommit(false);
            String insertQuery = "INSERT INTO upholsteries (manufacturer, code, name) VALUES (?, ?, ?);";

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                for (UpholsteryImportObject upholstery : upholsteries) {
                    statement.setString(1, upholstery.manufacturer);
                    statement.setString(2, upholstery.code);
                    statement.setString(3, upholstery.name);
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
