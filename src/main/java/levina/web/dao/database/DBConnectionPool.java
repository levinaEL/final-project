package levina.web.dao.database;

import levina.web.exceptions.ConnectionWaitTimeoutException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Levina on 21.08.2016.
 */
public final class DBConnectionPool {
    public final static Logger logger = Logger.getLogger(DBConnectionPool.class);

    public static DBConnectionPool getInstance() throws Exception{
        if(Singleton.INSTANCE.initException != null) {
            throw Singleton.INSTANCE.initException;
        }
        return Singleton.INSTANCE.connectionPool;
    }

    private enum Singleton {
        INSTANCE;

        Exception initException;
        DBConnectionPool connectionPool;

        Singleton() {
            try {
                ResourceBundle resource = ResourceBundle.getBundle("database");
                int poolSize = Integer.parseInt(resource.getString("db.poolSize"));
                String driver = resource.getString("db.driver");
                String url = resource.getString("db.url");
                String user = resource.getString("db.user");
                String pass = resource.getString("db.password");
                connectionPool = new DBConnectionPool(poolSize, driver, url, user, pass);
                initException = null;
            } catch (Exception e) {
                connectionPool = null;
                initException = e;
            }
        }
    }

    private final int poolSize;

    private final String driver;

    private final String connectionUrl;

    private final String userName;

    private final String userPassword;

    private ArrayBlockingQueue<Connection> pool;

    public DBConnectionPool(int poolSize, String driver, String connectionUrl, String userName, String userPassword) throws SQLException, ClassNotFoundException {
        this.poolSize = poolSize;
        this.driver = driver;
        this.connectionUrl = connectionUrl;
        this.userName = userName;
        this.userPassword = userPassword;
        initConnections();
    }
    private void initConnections() throws ClassNotFoundException, SQLException {
        pool = new ArrayBlockingQueue<>(poolSize);
        Class.forName(driver);
        while(pool.size() < poolSize) {
            pool.add(DriverManager.getConnection(connectionUrl, userName, userPassword));
        }
    }

    public Connection getConnection() {
        return getConnection(20);
    }

    public Connection getConnection(int tryCount) {
        Connection connection;
        try{
            connection = pool.remove();
        } catch (NoSuchElementException e) {
            connection = null;
        }
        if(connection == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.error("Interrupted exception: ", e);
            }
            if(tryCount <= 0) {
                throw new ConnectionWaitTimeoutException("Timeout of waiting available pool exceeded");
            }
            return getConnection(tryCount - 1);
        }
        return connection;
    }

    public void freeConnection(Connection connection) {
        pool.add(connection);
    }

}

