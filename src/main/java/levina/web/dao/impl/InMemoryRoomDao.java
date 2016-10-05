package levina.web.dao.impl;

import levina.web.constants.IRequestConstants;
import levina.web.dao.RoomDao;
import levina.web.dao.database.DBConnectionPool;
import levina.web.model.Room;
import levina.web.model.enums.RoomType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryRoomDao implementation of RoomDao
 */
public class InMemoryRoomDao implements RoomDao {
    public static Logger logger = Logger.getLogger(InMemoryRoomDao.class);

    public DBConnectionPool dbConnectionPool;

    private static final String GET_BY_ID_REQUEST
            = "SELECT r.*, t.cost FROM room r JOIN type_room t USING (room_type) WHERE r.room_id = ?";
    private static final String GET_BY_PARAMETER
            = "SELECT * FROM available_rooms WHERE " +
              "((numb_seats>=?) AND (room_type=?) AND (end_date < ? OR end_date IS NULL))";
    private static final String GET_ALL_REQUEST = "SELECT * FROM AVAILABLE_ROOMS";

    private InMemoryRoomDao() throws Exception {
        dbConnectionPool = DBConnectionPool.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private RoomDao instance;
        private Exception exception;

        Singleton() {
            try {
                instance = new InMemoryRoomDao();
            } catch (Exception e) {
                instance = null;
                exception = e;
            }
        }
    }

    public static RoomDao getInstance() throws Exception {
        if (Singleton.INSTANCE.instance == null) {
            throw Singleton.INSTANCE.exception;
        }
        return Singleton.INSTANCE.instance;
    }

    /**
     * Extract data from ResultSet and set it to Client object
     *
     * @param resultSet {ResultSet}
     * @return Client
     * @throws SQLException on failing extraction
     */
    private Room extractRoomFromResultSet(ResultSet resultSet) throws SQLException {
        Room room = new Room();

        room.setRoomID(resultSet.getLong(IRequestConstants.ROOM));
        room.setNumberSeats(resultSet.getInt(IRequestConstants.NUMBER_SEATS));
        room.setCost(resultSet.getDouble(IRequestConstants.COST));
        room.setRoomType(RoomType.valueOf(resultSet.getString(IRequestConstants.TYPE).toUpperCase()));

        return room;
    }

    /**
     *
     * @param roomId - room id
     * @return Room
     */
    @Override
    public Room getById(Long roomId) {

        ResultSet rs;
        Room room = null;
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_REQUEST);
            preparedStatement.setLong(1, roomId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                room = extractRoomFromResultSet(rs);
                room.setCost(rs.getDouble(IRequestConstants.COST));
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException e) {
            logger.error("SQL exception in getting room by id", e);
        } catch (Exception e) {
            logger.error("Exception in getting room by id", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return room;
    }

    /**
     * Select all rooms that are available and satisfy all the params
     * @param start - arrival date
     * @param personsCount - number of persons
     * @param roomType - room type
     * @return List
     */
    @Override
    public List<Room> getRoomsByParameters(Date start, int personsCount, RoomType roomType) {
        List<Room> rooms = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_PARAMETER);

            preparedStatement.setInt(1, personsCount);
            preparedStatement.setString(2, String.valueOf(roomType));
            preparedStatement.setDate(3, start);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(extractRoomFromResultSet(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (SQLException e) {
            logger.error("SQL exception in getting available rooms", e);
        } catch (Exception e) {
            logger.error("Exception in getting available rooms", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return rooms;
    }

    /**
     * Select all available rooms at the moment
     * @return List
     */
    @Override
    public List<Room> getAllAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_REQUEST);
            while (resultSet.next()) {
                rooms.add(extractRoomFromResultSet(resultSet));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            logger.error("SQL exception in getting all available rooms", e);
        } catch (Exception e) {
            logger.error("Exception in getting all available rooms", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return rooms;
    }

}
