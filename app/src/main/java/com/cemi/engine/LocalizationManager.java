package com.cemi.engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class LocalizationManager {

    private static HashMap<Locale, HashMap<String, String>> localizationMap = new HashMap<Locale, HashMap<String, String>>();

    private static Locale locale;

    public static void init() {
        setLanguage("en");
        try {
            // for each file in resources/localization/
            for (String fileName : FileUtils.getResourceFiles("/localization")) {
                // load the file as a string
                String localizationFile = FileUtils.loadFileAsString("/localization/" + fileName);
                HashMap<String, String> languageMap = new HashMap<String, String>();
                // for each line in the file
                for (String line : localizationFile.split("\n")) {
                    // split the line into key and value
                    String[] split = line.split("=");
                    // put the key and value into the map
                    languageMap.put(split[0], split[1]);
                    localizationMap.put(Locale.forLanguageTag(fileName), languageMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getLocalizedString(String key) {
        HashMap<String, String> languageMap = localizationMap.get(locale);
        if (languageMap == null)
            return null;
        return languageMap.get(key);
    }

    public static void setLanguage(String language) {
        
        locale = Locale.forLanguageTag(language);
    }

}
