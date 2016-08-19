package levina.web.dao.impl;

import levina.web.dao.RoomDao;
import levina.web.model.Room;
import levina.web.model.enums.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by MY on 18.08.2016.
 */
public class InMemoryRoomDao implements RoomDao {
    private static Logger logger = Logger.getLogger(InMemoryClientDao.class.getName());
    public static volatile InMemoryRoomDao instance = new InMemoryRoomDao();

    private InMemoryRoomDao() {
    }

    public void getAvailableRoomsTable(Date start, Date end) {

        try (Connection connection = ConnectorDB.getConnection()) {

            String createView =
                    "CREATE OR REPLACE VIEW AVAILABLE_ROOMS " +
                            "AS SELECT r.room_id, r.numb_seats, r.room_type, t.cost " +
                            "FROM room r JOIN type_room t USING (room_type) " +
                            "LEFT JOIN requests req ON ((req.room_id IS NULL AND req.status != 'cancel') OR (? > req.end_date)) " +
                            "GROUP BY r.room_id";
            PreparedStatement preparedStatement = connection.prepareStatement(createView);
            preparedStatement.setDate(1, start);
//            preparedStatement.setDate(2, end);

            preparedStatement.execute();

        } catch (SQLException sqle) {
            System.err.println("!* Test conection or creatin table failed:\n" + sqle.getLocalizedMessage());
        }
    }

    public Collection getRoomsByParameters(Date start, Date end, int personsCount, RoomType roomType) {
        getAvailableRoomsTable(start, end);
        Collection<Room> rooms = new ArrayList<>();

        String selectTableSQL = "SELECT * FROM available_rooms  WHERE  " +
                " numb_seats=? AND room_type=?";
        ResultSet rs;
        try (Connection connection = ConnectorDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);

            preparedStatement.setInt(1, personsCount);
            preparedStatement.setString(2, String.valueOf(roomType));

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long roomID = rs.getLong("room_id");
                double cost = rs.getDouble("cost");
                int numberSeats = rs.getInt("numb_seats");
                RoomType type = RoomType.valueOf(rs.getString("room_type").toUpperCase());

                Room room = new Room();
                room.setRoomID(roomID);
                room.setCost(cost);
                room.setRoomType(type);
                room.setNumberSeats(numberSeats);

                rooms.add(room);
            }

            preparedStatement.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return rooms;
    }
}
