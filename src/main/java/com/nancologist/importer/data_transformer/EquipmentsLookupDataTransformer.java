package com.nancologist.importer.data_transformer;

import com.google.gson.Gson;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquipmentsLookupDataTransformer {

    private static final String FILE_PATH = "src/main/resources/data/orig/BMW_&_Mini_Option_Codes.pdf";
    private static final Pattern equipmentPattern = Pattern.compile("([SP]\\w{3}A)\\s+(.+?)(?=\\s+[SP]\\w{3}A|$)");

    public static void main(String[] args) throws IOException {
        File pdfFile = new File(FILE_PATH);

        try (PDDocument pdf = Loader.loadPDF(pdfFile)) {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(pdf);

            Matcher equipmentMatcher = equipmentPattern.matcher(text);
            ArrayList<HashMap<String, String>> equipments = new ArrayList<>();
            while (equipmentMatcher.find()) {
                HashMap<String, String> equipment = new HashMap<>();
                equipment.put("code", equipmentMatcher.group(1).trim());
                equipment.put("description", equipmentMatcher.group(2).trim());
                equipments.add(equipment);
            }

            Gson gson = new Gson();
            System.out.println(gson.toJson(equipments));
        }
    }
}
