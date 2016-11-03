import com.ImageManager;
import db.ConfigDBUsage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


public class Main {
    public static String configFile = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties.";

    /**
     * This main method create a DB connection and run the program
     *
     * @param args
     */
    public static void main(String args[]) {
        File file = new File(configFile);
        Properties properties = null;
        try (FileInputStream fileInput = new FileInputStream(file)) {
            properties = new Properties();
            properties.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //connect to the DB
        ConfigDBUsage dbManager = new ConfigDBUsage(properties.getProperty("dbUrl"), properties.getProperty("userName"), properties.getProperty("password"), properties.getProperty("driver"));
        try {
            //create table if it doesn't exist
            dbManager.createTable();
            ImageManager imageManager = new ImageManager(dbManager, properties.getProperty("imagesFile"), properties.getProperty("outPutImagesFolder"));
            imageManager.downloadAndConvert();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
