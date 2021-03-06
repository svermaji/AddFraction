package com.sv.addfraction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DefaultConfigs {

    private URL propUrl;
    enum Config {
        QUESTION ("question");

        String val;
        Config (String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    String propFileName = "./conf.config";
    private final Properties configs = new Properties();
    private MyLogger logger;

    public DefaultConfigs(MyLogger logger) {
        this.logger = logger;
        initialize();
    }

    public void initialize() {
        readConfig();
    }

    public String getConfig(Config config) {
        if (configs.containsKey(config.getVal()))
            return configs.getProperty(config.getVal());
        return Utils.EMPTY;
    }

    private void readConfig() {
        logger.log ("Loading properties from path: " + propFileName);
        try (InputStream is = Files.newInputStream(Paths.get(propFileName))) {
            propUrl = Paths.get(propFileName).toUri().toURL();
            configs.load(is);
        } catch (Exception e) {
            logger.log ("Error in loading properties via file path, trying class loader. Message: " + e.getMessage());
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(propFileName)) {
                propUrl = Paths.get(propFileName).toUri().toURL();
                configs.load(is);
            } catch (IOException ioe) {
                logger.log ("Error in loading properties via class loader. Message: " + ioe.getMessage());
            } catch (RuntimeException exp) {
                logger.log ("Error in loading properties. Message: " + exp.getMessage());
            }
        }
        logger.log ("Prop url calculated as: " + propUrl);
    }

    public void saveConfig(AddFractions addFractions) {
        logger.log ("Saving properties at " + propUrl.getPath());
        configs.clear();
        configs.put(Config.QUESTION.getVal(), addFractions.getQuestions());
        logger.log ("Config is " + configs);
        try {
            configs.store(new FileOutputStream(propUrl.getPath()), null);
        } catch (IOException e) {
            logger.log ("Error in saving properties.");
        }
    }

}
