package com.bombo.cheatbot.conf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {

    private static final String CONFIGURATION_FOLDER = "./conf/";

    private Map<String, ApplicationConfiguration> configurationMap;

    public ConfigurationManager() {
        configurationMap = new HashMap<>();
        new File(CONFIGURATION_FOLDER).mkdirs();
    }

    public void loadAllConfiguration() {
        for (File file : new File(CONFIGURATION_FOLDER).listFiles()) {
            if(file.getName().endsWith("json")) {
                loadConfiguration(file.getName().replaceAll("\\.json", ""));
            }
        }
    }

    public ApplicationConfiguration loadConfiguration(String applicationName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ApplicationConfiguration ap = objectMapper.readValue(new File(CONFIGURATION_FOLDER + applicationName + ".json"), ApplicationConfiguration.class);
            configurationMap.put(ap.getApplicationExecutable(), ap);
            return ap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ApplicationConfiguration getOrLoadConfiguration(String applicationName) {
        if(!configurationMap.containsKey(applicationName)) {
            return loadConfiguration(applicationName);
        }
        return configurationMap.get(applicationName);
    }

    public void saveConfiguration() {
        configurationMap.forEach((k, v) -> {
            saveConfiguration(v);
        });
    }

    public void saveConfiguration(ApplicationConfiguration ap) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIGURATION_FOLDER + ap.getApplicationExecutable() + ".json"), ap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addConfiguration(ApplicationConfiguration applicationConfiguration) {
        configurationMap.put(applicationConfiguration.getApplicationExecutable(), applicationConfiguration);
    }
}
