import levina.web.dao.database.DBConnectionPool;
import levina.web.model.Room;
import levina.web.model.User;
import levina.web.service.logic.RoomService;
import levina.web.service.logic.UserService;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by MY on 09.09.2016.
 */
public class UnitTests extends Assert {
    public static Logger logger = Logger.getLogger(UnitTests.class);
    private DBConnectionPool dbConnectionPool;
    private Connection connection;
    private Statement statement;
    private UserService userService = new UserService();
    private RoomService roomService = new RoomService();
    private User user;
    private String login;
    private String password;
    private int numberSeats;
    private ResourceBundle resourceBundle ;


    @Before
    public void init() {
        resourceBundle = ResourceBundle.getBundle("test");
        login = resourceBundle.getString("user.login");
        password = resourceBundle.getString("user.password");
        numberSeats = Integer.parseInt(resourceBundle.getString("room.number"));
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.getConnection();
            statement = connection.createStatement();
        } catch (Exception e) {
            logger.error("Exception in testing", e);
        }

    }

    @After
    public void destroy(){
        try {
            statement.close();
        } catch (SQLException e) {
            logger.error("Exception in closing statement during test", e);
        }
        dbConnectionPool.freeConnection(connection);
    }

    @Test
    public void testSaveUser(){
        userService.createNew(login, password);
        user = userService.getUserByLogin(login);
        assertNotNull(user);
    }

    @Test
    public void testCheckPassword(){
        assertEquals(true, userService.checkPassword(login, password));
    }

    @Test
    public void testBooking(){
        Collection<Room> rooms = roomService.getAllAvailableRooms();
        assertNotNull(rooms);
    }
}
