package levina.web.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by MY on 08.08.2016.
 */
public class ConnectorDB {
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/hostel";
    public static final String JDBC_USER = "root";
    public static final String JDBC_PASSWORD = "leto19zima";

    public static Connection getConnection() throws SQLException {
   //     ResourceBundle resource = ResourceBundle.getBundle("hostel");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        String url = resource.getString("db.url");
//        String user = resource.getString("db.user");
//        String pass = resource.getString("db.password");

        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}