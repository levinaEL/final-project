package levina.web.dao;

import levina.web.model.User;

import java.util.Collection;

/**
 * UserDao defined methods which will be used during connection to DB, table users
 */
public interface UserDao {
    User getById(Long id);

    void save(User user);

    boolean checkPassword(String userLogin, String userPassword);

    User getUserByLogin(String login);

    Collection<User> getAll();
}
