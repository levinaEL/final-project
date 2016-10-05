package levina.web.service.logic;

import levina.web.dao.UserDao;
import levina.web.dao.impl.InMemoryUserDao;
import levina.web.model.User;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * UserService connect dao level with logic
 */
public class UserService {
    public static Logger logger = Logger.getLogger(UserService.class);
    private UserDao userDao;

    public UserService() {
        try {
            userDao = InMemoryUserDao.getInstance();
        } catch (Exception e) {
            logger.error("Exception in getting userDao instance", e);
        }
    }

    /**
     * get all users
     * @return Collection
     */
    public Collection<User> getAll() {
        return userDao.getAll();
    }

    /**
     * Create new user
     * @param login
     * @param password
     * @return boolean: if such user doesn't exist - true, otherwise - false
     */
    public boolean createNew(String login, String password) {
        User user = new User();
        user.setPassword(password);
        user.setLogin(login);
        if (userDao.getUserByLogin(login) == null) {
            userDao.save(user);
            return true;
        }
        return false;
    }

    public void createNew(String login, String password, boolean role) {
        User user = new User();
        user.setPassword(password);
        user.setAdmin(role);
        if (userDao.getUserByLogin(login) == null) {
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
        return userDao.getUserByLogin(email);
    }
}