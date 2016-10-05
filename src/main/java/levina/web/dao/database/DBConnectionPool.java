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
 * Realization of DB connection pool
 */
public final class DBConnectionPool {
    public final static Logger logger = Logger.getLogger(DBConnectionPool.class);
    public static final int DEFAULT_TRY_COUNT = 20; // Times to waiting locking connection
    public static final int TIME_TO_WAIT = 10; // Default time to wait connection if it's locked


    public static DBConnectionPool getInstance() throws Exception {
        if (Singleton.INSTANCE.initException != null) {
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

    private ArrayBlockingQueue<Connection> pool;

    // DB settings:
    private final String driver;

    private final String connectionUrl;

    private final String userName;

    private final String userPassword;

    /**
     * @param poolSize
     * @param driver        - DB driver name
     * @param connectionUrl - DB url
     * @param userName      - DB connection username
     * @param userPassword  - DB connection password
     * @throws SQLException
     * @throws ClassNotFoundException on driver not found
     */
    public DBConnectionPool(int poolSize, String driver, String connectionUrl, String userName, String userPassword) throws SQLException, ClassNotFoundException {
        this.poolSize = poolSize;
        this.driver = driver;
        this.connectionUrl = connectionUrl;
        this.userName = userName;
        this.userPassword = userPassword;
        initConnections();
    }

    private void initConnections() throws ClassNotFoundException, SQLException {
        pool = new ArrayBlockingQueue<>(poolSize); // initialize pool container
        Class.forName(driver); // load DB driver
        while (pool.size() < poolSize) {
            pool.add(DriverManager.getConnection(connectionUrl, userName, userPassword));
        }
    }

    public Connection getConnection() {
        return getConnection(DEFAULT_TRY_COUNT);
    }

    /**
     * @param tryCount - times of trying to accessing of free connection
     */
    public Connection getConnection(int tryCount) {
        Connection connection;
        try {
            connection = pool.remove(); // try to get connection
        } catch (NoSuchElementException e) {
            connection = null;
        }
        if (connection == null) {
            try {
                Thread.sleep(TIME_TO_WAIT);
            } catch (InterruptedException e) {
                logger.error("Interrupted exception: ", e);
            }
            if (tryCount <= 0) {
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

