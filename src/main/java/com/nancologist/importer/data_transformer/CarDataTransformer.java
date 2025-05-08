package com.nancologist.importer.data_transformer;

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

public class CarDataTransformer {
    static final private Path FILE_PATH = Paths.get("src/main/resources/data/orig/550iAT_F11_LCI_HR91.txt");
    /** VIN = Vehicle Identification Number */
    static final private Pattern vinPattern = Pattern.compile("(WBAHR91.+?)(?:\\s|$)");
    static final private Pattern colorPattern = Pattern.compile("(Colour|Farbe|Färg)\\s+(.+\\(\\w{2,}\\))(?:\\s|$)");
    static final private Pattern prodDatePattern = Pattern.compile("(Prod.date|Prod.-Datum|Produktionsdatum)\\s+([0-9]{4}-[0-9]{2}-[0-9]{2})");
    static final private Pattern upholsteryPattern = Pattern.compile("(Upholstery|Polsterung|Stoldynor)\\s+(.+\\(\\w+\\))");
    // Fixme: it does not find equipment codes of cars in english section (count=18), either fix the regex or add them manually
    static final private Pattern equipmentCodesPattern = Pattern.compile("^([SP]\\w{4})\\s");

    public static void main(String[] args) throws IOException {
        List<Object> cars = new ArrayList<>();

        try (Stream<String> lines = Files.lines(FILE_PATH)) {

            HashMap<String, Object> car = new HashMap<>();
            car.put("manufacturer", "BMW");
            car.put("model", "550i");
            car.put("bodyStyle", "Touring");
            car.put("bodyStyleCode", "F11");
            car.put("powerInKw", 330);
            car.put("powerUnit", "KW");
            car.put("displacementInLiter", 4.4);
            car.put("drive", "RWD");
            car.put("transmission", "automatic");
            car.put("doorsCount", 5);
            car.put("equipmentCodes", new ArrayList<String>());
            car.put("steering", "LHD");
            car.put("facelift", true);

            lines.forEach((line) -> {

                Matcher vinMatcher = vinPattern.matcher(line);
                if (vinMatcher.find()) {
                    if (car.get("vin") != null) {
                        cars.add(car.clone());
                        car.put("equipmentCodes", new ArrayList<String>());
                    }
                    String vin = vinMatcher.group(1);
                    car.put("vin", vin);
                }

                Matcher colorMatcher = colorPattern.matcher(line);
                if (colorMatcher.find()) {
                    car.put("color", colorMatcher.group(2));
                }

                Matcher prodDateMatcher = prodDatePattern.matcher(line);
                if (prodDateMatcher.find()) {
                    car.put("productionDate", prodDateMatcher.group(2));
                }

                Matcher upholsteryMatcher = upholsteryPattern.matcher(line);
                if (upholsteryMatcher.find()) {
                    car.put("upholstery", upholsteryMatcher.group(2));
                }

                Matcher equipmentCodesMatcher = equipmentCodesPattern.matcher(line);
                if (equipmentCodesMatcher.find()) {
                    ((ArrayList<String>) car.get("equipmentCodes")).add(equipmentCodesMatcher.group().trim());
                }
            });

             Gson gson = new Gson();
             System.out.println(gson.toJson(cars)); // Todo: instead of printing, create the json file directly
        }
    }
}
