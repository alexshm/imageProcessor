package db;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public class ConfigDBUsage extends ConfigDB {
    private static final Logger _logger = Logger.getRootLogger();

    public ConfigDBUsage(String DBAddress, String userName, String password, String driver) {
        super(DBAddress, userName, password, driver);
    }

    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS images "
                + "(downloadDate DATE, filepath VARCHAR(2000),"
                + "url VARCHAR(2000), md5 VARCHAR(32))";
        try (Statement s = createStatement()) {
            s.execute(query);
        }
    }

    public void insertIntoTable(String filepath,String url,String md5) throws SQLException {
        String query = "INSERT INTO images (downloadDate, filepath, url, md5) "
                + " VALUES(now() , '"+filepath.replace("\\","\\\\")+"','"+url.replace("\\","\\\\")+"','"+md5+"' )";
        try (Statement s = createStatement()) {
            s.execute(query);
            _logger.info("inserted data to the db :filepath"+filepath+", url:"+url+" ,md5:"+md5);
        }
    }


}
