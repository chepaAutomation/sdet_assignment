package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/***
 * Purpose : method to access the config which is used across project which is maintained in different properties file
 */

public class ConfigReader {
    private static Properties applicationProperties = new Properties();

    static {
        try {
            applicationProperties.load(new FileInputStream("./src/main/resources/application.properties"));
        } catch (IOException e) {
            LogUtil.getLogger().error("Failed to read application.properties file");
        }
    }

    public static Properties getApplicationProperties() {
        return applicationProperties;
    }

}
