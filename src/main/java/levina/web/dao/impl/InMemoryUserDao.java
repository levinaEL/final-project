package levina.web.dao.impl;

import levina.web.constants.IUserConstants;
import levina.web.dao.UserDao;
import levina.web.dao.database.DBConnectionPool;
import levina.web.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * InMemoryUserDao implementation of UserDao
 */
public class InMemoryUserDao implements UserDao {
    public static Logger logger = Logger.getLogger(InMemoryUserDao.class);
    public DBConnectionPool dbConnectionPool;
    public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ? ";
    public static final String SELECT_CHECK_PASSWORD = "SELECT * FROM users WHERE user_login=? and user_password=md5(?)";
    public static final String INSERT_USER = "insert into users (user_login, user_password, is_admin) " +
            "values(?, md5(?), ?)";
    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE user_login = ? ";
    public static final String SELECT_ALL_USERS = "SELECT * FROM users";

    private InMemoryUserDao() throws Exception {
        dbConnectionPool = DBConnectionPool.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private UserDao instance;
        private Exception exception;

        Singleton() {
            try {
                instance = new InMemoryUserDao();
            } catch (Exception e) {
                instance = null;
                exception = e;
            }
        }
    }

    public static UserDao getInstance() throws Exception {
        if (Singleton.INSTANCE.instance == null) {
            throw Singleton.INSTANCE.exception;
        }
        return Singleton.INSTANCE.instance;
    }

    /**
     * Extract data from ResultSet and set it to User object
     *
     * @param rs {ResultSet}
     * @return Client
     * @throws SQLException on failing extraction
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        Long id = rs.getLong(IUserConstants.USER_ID);
        if (!rs.wasNull()) {
            user.setId(id);
        }
        user.setLogin(rs.getString(IUserConstants.LOGIN).trim());
        user.setPassword(rs.getString(IUserConstants.PASSWORD).trim());
        user.setAdmin(rs.getBoolean("is_admin"));

        return user;
    }

    /**
     * Forms preparedStatement based on user information for the next execution
     *
     * @param user            -
     * @param preparedStatement {PreparedStatement}
     * @throws SQLException
     */
    private void prepareExecuteStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setBoolean(3, false);
    }

    /**
     *
     * @param id - user id
     * @return User
     */
    @Override
    public User getById(Long id) {
        User user = null;
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = extractUserFromResultSet(resultSet);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            logger.error("SQL exception in getting user by id", e);
        } catch (Exception e) {
            logger.error("Exception in getting user by id", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return user;
    }

    /**
     * Checking if such user exist
     * @param userLogin - required login
     * @param password - required password
     * @return boolean - if it's matched - true, otherwise - false
     */
    @Override
    public boolean checkPassword(String userLogin, String password) {
        boolean isMatched = false;
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();

            PreparedStatement stmt = connection.prepareStatement(SELECT_CHECK_PASSWORD);
            stmt.setString(1, userLogin);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            isMatched = rs.next();

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.error("SQL exception in checking password", e);
        } catch (Exception e) {
            logger.error("Exception in checking password", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return isMatched;
    }
    /**
     * Insert record into users table
     *
     * @param user - object that need to be save
     */
    @Override
    public void save(User user) {
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);

            prepareExecuteStatement(user, preparedStatement);
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            logger.error("SQL exception in checking password", e);
        } catch (Exception e) {
            logger.error("Exception in checking password", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
    }


    /**
     *
     * @param userLogin - param for searching
     * @return User
     */
    @Override
    public User getUserByLogin(String userLogin) {
        Connection connection = null;
        User user = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUserFromResultSet(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            logger.error("SQL exception in getting user by login", e);
        } catch (Exception e) {
            logger.error("Exception in getting user by login", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return user;
    }

    /**
     * Get all records from users table
     * @return Collection
     */
    @Override
    public Collection<User> getAll() {
        Collection<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                users.add(extractUserFromResultSet(resultSet));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            logger.error("SQL exception in getting users", e);
        } catch (Exception e) {
            logger.error("Exception in getting users", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return users;
    }
}
