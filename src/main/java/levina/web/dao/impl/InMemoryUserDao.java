package levina.web.dao.impl;

/**
 * Created by MY on 07.08.2016.
 */


import levina.web.dao.DBConnectionPool;
import levina.web.dao.UserDao;
import levina.web.model.User;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;



public class InMemoryUserDao implements UserDao {
    public static Logger logger = Logger.getLogger(InMemoryUserDao.class);
    public static volatile InMemoryUserDao instance = new InMemoryUserDao();

    public DBConnectionPool dbConnectionPool;

    private InMemoryUserDao() {
    }

    @Override
    public User getById(Long id) {
        String selectTableSQL = "SELECT * FROM users WHERE user_id = ? ";
        ResultSet rs;
        User user = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String login = rs.getString("user_login").trim();
                String password = rs.getString("user_password").trim();
                boolean role = rs.getBoolean("is_admin");

                user = new User();

                user.setLogin(login);
                user.setPassword(password);
                user.setAdmin(role);
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("SQL exception in getting user by id", e);
        } catch (Exception e) {
            logger.error("Exception in getting user by id", e);
        }
        return user;
    }

    @Override
    public boolean checkPassword(String userLogin, String password) {
        boolean isMatched = false;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            String sql = "SELECT * FROM users WHERE user_login=? and user_password=md5(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, userLogin);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            isMatched = rs.next();

            rs.close();
            stmt.close();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e ) {
            logger.error("SQL exception in checking password", e);
        } catch (Exception e) {
            logger.error("Exception in checking password", e);
        }
        return isMatched;
    }

    @Override
    public void save(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        boolean role = false;

        String insertTableSQL = "insert into users (user_login, user_password, is_admin) " +
                "values(?, md5(?), ?)";
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setBoolean(3, role);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in checking password", e);
        } catch (Exception e) {
            logger.error("Exception in checking password", e);
        }
    }


    @Override
    public User getEntityBy(String userLogin) {
        String selectTableSQL = "SELECT * FROM users WHERE user_login = ? ";
        ResultSet rs;
        User user = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setString(1, userLogin);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {

                user = new User();
                long id = rs.getLong("user_id");
                String pass = rs.getString("user_password").trim();
                boolean role = rs.getBoolean("is_admin");

                user.setId(id);
                user.setLogin(userLogin);
                user.setPassword(pass);
                user.setAdmin(role);
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in getting user by login", e);
        } catch (Exception e) {
            logger.error("Exception in getting user by login", e);
        }
        return user;
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> users = new ArrayList<User>();
        String selectTableSQL = "SELECT "
                + "user_id, "
                + "user_login, "
                + "from users";
        ResultSet rs;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Long id = Long.parseLong(rs.getString("user_id").trim());
                String login = rs.getString("user_login").trim();

                User user = new User();
                user.setId(id);
                user.setLogin(login);

                users.add(user);
            }
            statement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in getting users", e);
        } catch (Exception e) {
            logger.error("Exception in getting users", e);
        }
        return users;
    }
}
