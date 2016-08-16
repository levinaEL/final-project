package levina.web.dao.impl;

/**
 * Created by MY on 07.08.2016.
 */


import levina.web.dao.UserDao;
import levina.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InMemoryUserDao implements UserDao {

    private static Logger logger = Logger.getLogger(InMemoryUserDao.class.getName());
    public static volatile InMemoryUserDao instance = new InMemoryUserDao();
    public final static boolean ADMIN = true;
    public final static boolean USER = false;


    private InMemoryUserDao() {
    }

    @Override
    public User getById(Long id) {
        String selectTableSQL = "SELECT * FROM users "
                + "WHERE user_id = ?";
        ResultSet rs;
        User user = null;
        try (Connection connection = ConnectorDB.getConnection()){

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
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @Override
    public boolean checkPassword(String userLogin, String password) {
        boolean isMatched = false;
        try {
            Connection connection = ConnectorDB.getConnection();
            String sql = "SELECT * FROM users WHERE user_login=? and user_password=md5(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, userLogin);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            isMatched = rs.next();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return isMatched;
    }

    @Override
    public void save(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        boolean role = USER;

        String insertTableSQL = "insert into users (user_login, user_password, is_admin) " +
                "values(?, md5(?), ?)";
        try (Connection connection = ConnectorDB.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setBoolean(3, role);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public User getEntityBy(String userLogin) {
        String selectTableSQL = "SELECT * FROM users WHERE user_login = ?";
        ResultSet rs;
        User user = null;
        try ( Connection connection = ConnectorDB.getConnection()){
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
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
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
            Connection connection = ConnectorDB.getConnection();
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
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return users;
    }
}
