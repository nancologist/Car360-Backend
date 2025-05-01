package com.nancologist.data_transformer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarDataTransformer {

    public static void main(String[] args) {
        String filePath = "src/main/resources/data/orig/550iAT_F11_LCI_HR91.txt";
        try {
            String jsonData = transformCarData(filePath);
            System.out.println(jsonData);
            // You can then save this jsonData to a file.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String transformCarData(String filePath) throws IOException {
        List<Map<String, String>> carDataList = new ArrayList<>();
        Map<String, Map<String, String>> equipmentLookup = new HashMap<>();
        StringBuilder jsonBuilder = new StringBuilder();

        // Find out the current working directory to debug the file path if you get "File not found":
//        String currentWorkingDirectory = System.getProperty("user.dir");
//        System.out.println("Current working directory: " + currentWorkingDirectory);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Map<String, String> currentCar = null;
            String currentSection = null;

            Pattern chassisPattern = Pattern.compile("Chassinummer\\s+(.+)");
            Pattern typeCodePattern = Pattern.compile("Typkod\\s+(.+)");
            Pattern typePattern = Pattern.compile("Typ\\s+(.+)");
            Pattern eModelSeriesPattern = Pattern.compile("E-modellserie\\s+(.+)");
            Pattern seriesPattern = Pattern.compile("Serie\\s+(.+)");
            Pattern bodyTypePattern = Pattern.compile("Typ\\s+TOUR"); // Assuming "TOUR" is the English for the second "Typ"
            Pattern steeringPattern = Pattern.compile("Styrning\\s+(.+)");
            Pattern doorsPattern = Pattern.compile("Dörrar\\s+(.+)");
            Pattern enginePattern = Pattern.compile("Motor\\s+(.+)");
            Pattern cylinderVolumePattern = Pattern.compile("Cylindervolym\\s+(.+)");
            Pattern enginePowerPattern = Pattern.compile("Motoreffekt\\s+(.+)");
            Pattern engineLocationPattern = Pattern.compile("Motor\\s+(HECK)"); // Assuming "HECK" always means RWD
            Pattern transmissionPattern = Pattern.compile("Växellåda\\s+(.+)");
            Pattern colorPattern = Pattern.compile("Färg\\s+(.+)");
            Pattern upholsteryPattern = Pattern.compile("Stoldynor\\s+(.+)");
            Pattern productionDatePattern = Pattern.compile("Produktionsdatum\\s+(.+)");

            Pattern equipmentCodeInterfacePattern = Pattern.compile("S[0-9A-Z]{3}A\\s+(.+?)\\s{2,}");
            Pattern equipmentCodeEPCPattern = Pattern.compile("S[0-9A-Z]{3}A\\s+.+?\\s{2,}(.+)");
            Pattern standardEquipmentCodePattern = Pattern.compile("S[0-9A-Z]{3}A");


            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.contains("Fordonsuppgifter")) {
                    currentCar = new HashMap<>();
                    currentCar.put("optional_equipment", ""); // Initialize as empty string
                    currentCar.put("standard_equipment", ""); // Initialize as empty string
                    currentSection = "Fordonsuppgifter";
                } else if (line.contains("Extrautrustning")) {
                    currentSection = "Extrautrustning";
                } else if (line.contains("Serieutrustning")) {
                    currentSection = "Serieutrustning";
                } else if (line.contains("Individualfordonsdata")) {
                    currentSection = "Individualfordonsdata";
                } else if (line.isEmpty() && currentCar != null) {
                    carDataList.add(currentCar);
                    currentCar = null;
                    currentSection = null;
                } else if (currentCar != null) {
                    Matcher chassisMatcher = chassisPattern.matcher(line);
                    if (chassisMatcher.find()) {
                        currentCar.put("chassis_number", chassisMatcher.group(1).trim());
                        continue;
                    }
                    Matcher typeCodeMatcher = typeCodePattern.matcher(line);
                    if (typeCodeMatcher.find()) {
                        currentCar.put("type_code", typeCodeMatcher.group(1).trim());
                        continue;
                    }
                    Matcher typeMatcher = typePattern.matcher(line);
                    if (typeMatcher.find() && currentCar.get("type") == null) {
                        currentCar.put("type", typeMatcher.group(1).trim());
                        continue;
                    }
                    Matcher eModelSeriesMatcher = eModelSeriesPattern.matcher(line);
                    if (eModelSeriesMatcher.find()) {
                        currentCar.put("model_series", eModelSeriesMatcher.group(1).trim());
                        continue;
                    }
                    Matcher seriesMatcher = seriesPattern.matcher(line);
                    if (seriesMatcher.find()) {
                        currentCar.put("series", seriesMatcher.group(1).trim());
                        continue;
                    }
                    Matcher bodyTypeMatcher = bodyTypePattern.matcher(line);
                    if (bodyTypeMatcher.find()) {
                        currentCar.put("body_type", "TOUR"); // Hardcoded based on your example
                        continue;
                    }
                    Matcher steeringMatcher = steeringPattern.matcher(line);
                    if (steeringMatcher.find()) {
                        currentCar.put("steering", steeringMatcher.group(1).trim());
                        continue;
                    }
                    Matcher doorsMatcher = doorsPattern.matcher(line);
                    if (doorsMatcher.find()) {
                        currentCar.put("doors", doorsMatcher.group(1).trim());
                        continue;
                    }
                    Matcher engineMatcher = enginePattern.matcher(line);
                    if (engineMatcher.find() && !line.contains("HECK")) {
                        currentCar.put("engine", engineMatcher.group(1).trim());
                        continue;
                    }
                    Matcher cylinderVolumeMatcher = cylinderVolumePattern.matcher(line);
                    if (cylinderVolumeMatcher.find()) {
                        currentCar.put("cylinder_volume", cylinderVolumeMatcher.group(1).trim());
                        continue;
                    }
                    Matcher enginePowerMatcher = enginePowerPattern.matcher(line);
                    if (enginePowerMatcher.find()) {
                        currentCar.put("engine_power", enginePowerMatcher.group(1).trim());
                        continue;
                    }
                    Matcher engineLocationMatcher = engineLocationPattern.matcher(line);
                    if (engineLocationMatcher.find()) {
                        currentCar.put("engine_location", "RWD"); // Hardcoded based on your instruction
                        currentCar.put("drive_train", "RWD"); // Renamed property
                        continue;
                    }
                    Matcher transmissionMatcher = transmissionPattern.matcher(line);
                    if (transmissionMatcher.find()) {
                        currentCar.put("transmission", transmissionMatcher.group(1).trim());
                        continue;
                    }
                    Matcher colorMatcher = colorPattern.matcher(line);
                    if (colorMatcher.find()) {
                        currentCar.put("color", colorMatcher.group(1).trim());
                        continue;
                    }
                    Matcher upholsteryMatcher = upholsteryPattern.matcher(line);
                    if (upholsteryMatcher.find()) {
                        currentCar.put("upholstery", upholsteryMatcher.group(1).trim());
                        continue;
                    }
                    Matcher productionDateMatcher = productionDatePattern.matcher(line);
                    if (productionDateMatcher.find()) {
                        currentCar.put("production_date", productionDateMatcher.group(1).trim());
                        continue;
                    }

                    if (currentSection != null) {
                        if (currentSection.equals("Extrautrustning") || currentSection.equals("Serieutrustning")) {
                            Matcher codeMatcher = standardEquipmentCodePattern.matcher(line);
                            if (codeMatcher.find()) {
                                String code = codeMatcher.group(0).trim();
                                if (currentSection.equals("Extrautrustning")) {
                                    currentCar.put("optional_equipment", currentCar.get("optional_equipment") + "\"" + code + "\",");
                                } else if (currentSection.equals("Serieutrustning")) {
                                    currentCar.put("standard_equipment", currentCar.get("standard_equipment") + "\"" + code + "\",");
                                }
                                Matcher interfaceDescMatcher = equipmentCodeInterfacePattern.matcher(line);
                                Matcher epcDescMatcher = equipmentCodeEPCPattern.matcher(line);
                                if (interfaceDescMatcher.find() && epcDescMatcher.find()) {
                                    Map<String, String> descriptions = new HashMap<>();
                                    descriptions.put("description_interface", interfaceDescMatcher.group(1).trim());
                                    descriptions.put("description_epc", epcDescMatcher.group(1).trim());
                                    equipmentLookup.put(code, descriptions);
                                }
                            }
                        } else if (currentSection.equals("Individualfordonsdata") && line.contains("no access")) {
                            currentCar.put("individual_vehicle_data", "no access - Vehicle data protected");
                        }
                    }
                }
            }
        }

        // Build JSON String
        jsonBuilder.append("{\n");

        // Equipment Code Lookup
        jsonBuilder.append("  \"equipment_code_lookup\": [\n");
        int equipmentCount = 0;
        for (Map.Entry<String, Map<String, String>> entry : equipmentLookup.entrySet()) {
            jsonBuilder.append("    {\n");
            jsonBuilder.append("      \"code\": \"").append(entry.getKey()).append("\",\n");
            jsonBuilder.append("      \"description_interface\": \"").append(entry.getValue().get("description_interface")).append("\",\n");
            jsonBuilder.append("      \"description_epc\": \"").append(entry.getValue().get("description_epc")).append("\"\n");
            jsonBuilder.append("    }");
            if (equipmentCount < equipmentLookup.size() - 1) {
                jsonBuilder.append(",\n");
            } else {
                jsonBuilder.append("\n");
            }
            equipmentCount++;
        }
        jsonBuilder.append("  ],\n");

        // Cars Array
        jsonBuilder.append("  \"cars\": [\n");
        int carCount = 0;
        for (Map<String, String> carData : carDataList) {
            jsonBuilder.append("    {\n");
            int propertyCount = 0;
            for (Map.Entry<String, String> entry : carData.entrySet()) {
                jsonBuilder.append("      \"").append(entry.getKey()).append("\": ");
                if (entry.getKey().equals("optional_equipment") || entry.getKey().equals("standard_equipment")) {
                    // Remove trailing comma and wrap in brackets for arrays
                    String value = entry.getValue().replaceAll(",$", "");
                    jsonBuilder.append("[").append(value).append("]");
                } else {
                    jsonBuilder.append("\"").append(entry.getValue()).append("\"");
                }


                if (propertyCount < carData.size() - 1) {
                    jsonBuilder.append(",\n");
                } else {
                    jsonBuilder.append("\n");
                }
                propertyCount++;
            }
            jsonBuilder.append("    }");
            if (carCount < carDataList.size() - 1) {
                jsonBuilder.append(",\n");
            } else {
                jsonBuilder.append("\n");
            }
            carCount++;
        }
        jsonBuilder.append("  ]\n");
        jsonBuilder.append("}\n");

        return jsonBuilder.toString();
    }
}