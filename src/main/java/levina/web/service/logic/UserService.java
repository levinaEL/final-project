package levina.web.service.logic;

import levina.web.dao.UserDao;
import levina.web.dao.impl.InMemoryUserDao;
import levina.web.model.User;

import java.util.Collection;

/**
 * Created by MY on 07.08.2016.
 */
public class UserService {

    private UserDao userDao;

    public UserService() {
        userDao = InMemoryUserDao.instance;
    }

    public Collection<User> getAll() {
        return userDao.getAll();
    }

    public boolean createNew(String login, String password) {
        User user = new User();
        user.setPassword(password);
        user.setLogin(login);
        if (userDao.getEntityBy(login) == null) {
            userDao.save(user);
            return true;
        }
        return false;
    }

    public void createNew(String login, String password, boolean role) {
        User user = new User();
        user.setPassword(password);
        user.setAdmin(role);
        if (userDao.getEntityBy(login) == null) {
            userDao.save(user);
        }
    }

    public User getById(Long id) {
        return userDao.getById(id);
    }

    public boolean checkPassword(String email, String password) {
        return userDao.checkPassword(email, password);
    }

    public User getUserByLogin(String email) {
        return userDao.getEntityBy(email);
    }
}