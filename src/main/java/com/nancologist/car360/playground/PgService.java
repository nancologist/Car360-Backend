package com.nancologist.car360.playground;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PgService {
    private static final String PATH = "src/main/resources/PG_USERS.csv";
    private final List<PgUser> pgUsers = new ArrayList<>();

    public PgService() {
        this.parseUsers();
    }

    public List<PgUser> findUsers(String search) {
        if (search == null) return pgUsers;
        return pgUsers.stream().filter(u -> u.getFirstName().toLowerCase().contains(search)).toList();
    }

    private void parseUsers() {

        try (java.io.Reader reader = new FileReader(PATH)) {

            for (CSVRecord csvRecord : getCsvParser(reader)) {

                PgUser pgUser = new PgUser(
                        Long.parseLong(csvRecord.get("id").trim()),
                        csvRecord.get("first_name").trim(),
                        csvRecord.get("last_name").trim(),
                        csvRecord.get("email").trim(),
                        csvRecord.get("ip_address").trim(),
                        LocalDate.parse(csvRecord.get("birth_date").trim()),
                        csvRecord.get("country").trim()
                );
                pgUsers.add(pgUser);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CSVParser getCsvParser(Reader reader) throws IOException {
        return CSVFormat
                .Builder
                .create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get()
                .parse(reader);
    }
}
