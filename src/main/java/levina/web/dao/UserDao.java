package levina.web.dao;

import levina.web.model.User;

import java.util.Collection;

/**
 * Created by MY on 15.08.2016.
 */
public interface UserDao {
    User getById(Long id);

    void save(User user);

    boolean checkPassword(String userLogin, String userPassword);

    User getEntityBy(String login);

    Collection<User> getAll();
}
