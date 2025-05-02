package com.nancologist.data_transformer;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CarDataTransformer2 {
    static final private String FILE_PATH = "src/main/resources/data/orig/550iAT_F11_LCI_HR91.txt";
    /** VIN = Vehicle Identification Number */
    static final private Pattern vinPattern = Pattern.compile("(WBAHR91.+?)(?:\\s|$)");
    static final private Pattern colorPattern = Pattern.compile("(Colour|Farbe|Färg)\\s+(.+\\(\\w{2,}\\))(?:\\s|$)");

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(FILE_PATH);
        List<Object> cars = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {

            HashMap<String, String> car = new HashMap<>();
            lines.forEach((line) -> {

                Matcher vinMatcher = vinPattern.matcher(line);
                if (vinMatcher.find()) {
                    if (!car.isEmpty()) {
                        cars.add(car.clone());
                    }
                    car.clear(); // If a new car then reset the map
                    String vin = vinMatcher.group(1);
                    car.put("VIN", vin);
                }

                Matcher colorMatcher = colorPattern.matcher(line);
                if (colorMatcher.find()) {
                    car.put("color", colorMatcher.group(2));
                }
            });

             Gson gson = new Gson();
             System.out.println(gson.toJson(cars));
        }
    }
}
