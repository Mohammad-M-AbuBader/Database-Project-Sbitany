/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 16-5-2021  11:35 AM
 */
package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionToSbitanyDatabase {

    private String sbURL;
    private final String sbUsername;
    private final String sbPassword;
    private final String URl;
    private final String port;
    private final String sbName;

    public ConnectionToSbitanyDatabase() {
        this.URl = "127.0.0.1";
        this.port = "3306";
        this.sbName = "SBITANY";
        this.sbUsername = "root";
        this.sbPassword = "406759167";
    }

    public Connection connectSbitanyDB() {
        try {
            sbURL = "jdbc:mysql://" + this.URl + ":" + this.port + "/" + this.sbName + "?verifyServerCertificate=false";
            Properties properties = new Properties();
            properties.setProperty("user", sbUsername);
            properties.setProperty("password", sbPassword);
            properties.setProperty("useSSl", "false");
            properties.setProperty("autoReconnect", "true");
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(sbURL, properties);

        } catch (ClassNotFoundException | SQLException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return null;
        }
    }
}
