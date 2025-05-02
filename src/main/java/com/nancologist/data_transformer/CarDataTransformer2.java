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
    static final private Pattern prodDatePattern = Pattern.compile("(Prod.date|Prod.-Datum|Produktionsdatum)\\s+([0-9]{4}-[0-9]{2}-[0-9]{2})");
    static final private Pattern upholsteryPattern = Pattern.compile("(Upholstery|Polsterung|Stoldynor)\\s+(.+\\(\\w+\\))");

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(FILE_PATH);
        List<Object> cars = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {

            HashMap<String, String> car = new HashMap<>();
            car.put("manufacturer", "BMW");
            car.put("model", "550i");
            car.put("bodyStyle", "Touring");
            car.put("bodyStyleCode", "F11");
            car.put("power", "330 KW");
            car.put("displacement", "4.4 L");
            car.put("drive", "RWD");
            car.put("transmission", "automatic");
            car.put("doors", "5");

            lines.forEach((line) -> {

                Matcher vinMatcher = vinPattern.matcher(line);
                if (vinMatcher.find()) {
                    if (!car.isEmpty()) {
                        cars.add(car.clone());
                    }
                    // car.clear(); // If a new car then reset the map
                    String vin = vinMatcher.group(1);
                    car.put("vin", vin);
                }

                Matcher colorMatcher = colorPattern.matcher(line);
                if (colorMatcher.find()) {
                    car.put("color", colorMatcher.group(2));
                }

                Matcher prodDateMatcher = prodDatePattern.matcher(line);
                if (prodDateMatcher.find()) {
                    car.put("prodDate", prodDateMatcher.group(2));
                }

                Matcher upholsteryMatcher = upholsteryPattern.matcher(line);
                if (upholsteryMatcher.find()) {
                    car.put("upholstery", upholsteryMatcher.group(2));
                }
            });

             Gson gson = new Gson();
             System.out.println(gson.toJson(cars)); // Todo: instead of printing, create the json file directly
        }
    }
}
