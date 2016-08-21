package levina.web.dao.impl;

import levina.web.dao.RequestDao;
import levina.web.model.Request;
import levina.web.model.enums.RoomType;
import levina.web.model.enums.StatusRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by MY on 14.08.2016.
 */
public class InMemoryRequestDao implements RequestDao {
    private static Logger logger = Logger.getLogger(InMemoryClientDao.class.getName());
    public static volatile InMemoryRequestDao instance = new InMemoryRequestDao();

    private InMemoryRequestDao() {
    }

    @Override
    public Request getById(Long id) {
        String selectTableSQL = "SELECT * FROM requests "
                + "WHERE req_id = ?";
        ResultSet rs;
        Request request = null;
        try (Connection connection = ConnectorDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long requestID = rs.getLong("req_id");
                Long roomID = rs.getLong("room_id");
                Long clientID = rs.getLong("client_id");
                RoomType roomType = RoomType.valueOf(rs.getString("room_type").toUpperCase());
                Timestamp reqDate = rs.getTimestamp("req_date");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int count = rs.getInt("persons_count");
                StatusRequest status = StatusRequest.valueOf(rs.getString("status").toUpperCase());

                request = new Request();

                request.setClientID(clientID);
                request.setRequestID(requestID);
                request.setRoomID(roomID);
                request.setRoomType(roomType);
                request.setRequestDate(reqDate);
                request.setStartDate(startDate);
                request.setEndDate(endDate);
                request.setPersonsCount(count);
                request.setStatusRequest(status);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return request;
    }

    @Override
    public Request getByClientId(Long id) {
        String selectTableSQL = "SELECT * FROM requests  "
                + "WHERE client_id = ?";
        ResultSet rs;
        Request request = null;
        try (Connection connection = ConnectorDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long requestID = rs.getLong("req_id");
                Long roomID = rs.getLong("room_id");
                Long clientID = rs.getLong("client_id");
                RoomType roomType = RoomType.valueOf(rs.getString("room_type").toUpperCase());
                Timestamp reqDate = rs.getTimestamp("req_date");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int count = rs.getInt("persons_count");
                StatusRequest status = StatusRequest.valueOf(rs.getString("status").toUpperCase());

                request = new Request();

                request.setClientID(clientID);
                request.setRequestID(requestID);
                request.setRoomID(roomID);
                request.setRoomType(roomType);
                request.setRequestDate(reqDate);
                request.setStartDate(startDate);
                request.setEndDate(endDate);
                request.setPersonsCount(count);
                request.setStatusRequest(status);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return request;
    }

@Override
    public void save(Request request) {
        Long clientID = request.getClientID();
        Long roomID = request.getRoomID();
        RoomType roomType = request.getRoomType();
        Timestamp reqDate = request.getRequestDate();
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        int count = request.getPersonsCount();
        StatusRequest status = request.getStatusRequest();

        String insertTableSQL = "insert into requests (client_id, room_id, room_type, req_date, start_date, end_date, persons_count, status) " +
                "values(?,?,?,?,?,?,?,?)";
        try {
            Connection connection = ConnectorDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            if(clientID != null) {
                preparedStatement.setLong(1, clientID);
            }else{
                preparedStatement.setNull(1, Types.INTEGER);
            }
            if(roomID != null) {
                preparedStatement.setLong(2, roomID);
            }else{
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setString(3, String.valueOf(roomType).toUpperCase());
            preparedStatement.setTimestamp(4, reqDate);
            preparedStatement.setDate(5, startDate);
            preparedStatement.setDate(6, endDate);
            preparedStatement.setInt(7, count);
            preparedStatement.setString(8, String.valueOf(status).toUpperCase());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void cancel(Long id) {
        String deleteFromTableSQL = "UPDATE requests SET status = ? WHERE req_id = ?";
        try (Connection connection = ConnectorDB.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(deleteFromTableSQL);
            preparedStatement.setString(1, String.valueOf(StatusRequest.CANCEL));
            preparedStatement.setLong(2, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void approve(Long requestId, Long roomId) {
        String deleteFromTableSQL = "UPDATE requests SET status = ?, room_id = ? WHERE req_id = ? ";
        try (Connection connection = ConnectorDB.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(deleteFromTableSQL);
            preparedStatement.setString(1, String.valueOf(StatusRequest.APPROVED).toUpperCase());
            preparedStatement.setLong(2, roomId);
            preparedStatement.setLong(3, requestId);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public Collection<Request> getAdminRequests() {
        Collection<Request> requests = new ArrayList<>();
        String selectTableSQL = "SELECT * FROM requests JOIN clients c USING (client_id) " +
                "WHERE status = 'pending' AND start_date > CURRENT_DATE ";
        ResultSet rs;
        try (Connection connection = ConnectorDB.getConnection()){

            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Long id = Long.parseLong(rs.getString("req_id"));
                Long clientID = Long.parseLong(rs.getString("client_id"));
                RoomType type = RoomType.valueOf(rs.getString("room_type").toUpperCase());
                Timestamp reqDate = rs.getTimestamp("req_date");
                Date start = rs.getDate("start_date");
                Date end = rs.getDate("end_date");
                int persons = rs.getInt("persons_count");
                StatusRequest status = StatusRequest.valueOf(rs.getString("status").toUpperCase());

                Request request = new Request();
                request.setRequestID(id);
                request.setClientID(clientID);
                request.setRoomType(type);
                request.setRequestDate(reqDate);
                request.setStartDate(start);
                request.setEndDate(end);
                request.setPersonsCount(persons);
                request.setStatusRequest(status);

                requests.add(request);
            }
            statement.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return requests;
    }


    @Override
    public Collection<Request> getAllClientsRequests(Long clientID) {
        Collection<Request> requests = new ArrayList<>();
        String selectTableSQL = "SELECT * FROM requests WHERE " +
                "client_id = ? AND status != ? AND start_date > CURRENT_DATE ";
        ResultSet rs;
        try (Connection connection = ConnectorDB.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, clientID);
            preparedStatement.setString(2, String.valueOf(StatusRequest.APPROVED));
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long reqID = rs.getLong("req_id");
                RoomType type = RoomType.valueOf(rs.getString("room_type").toUpperCase());
                Timestamp reqDate = rs.getTimestamp("req_date");
                Date start = rs.getDate("start_date");
                Date end = rs.getDate("end_date");
                int persons = rs.getInt("persons_count");
                StatusRequest status = StatusRequest.valueOf(rs.getString("status").toUpperCase());

                Request request = new Request();
                request.setRequestID(reqID);
                request.setClientID(clientID);
                request.setRoomType(type);
                request.setRequestDate(reqDate);
                request.setStartDate(start);
                request.setEndDate(end);
                request.setPersonsCount(persons);
                request.setStatusRequest(status);

                requests.add(request);
            }

            preparedStatement.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return requests;
    }

    @Override
    public Collection<Request> getAll() {
        Collection<Request> requests = new ArrayList<>();
        String selectTableSQL = "SELECT * FROM requests WHERE status != 'pending'";
        ResultSet rs;
        try (Connection connection = ConnectorDB.getConnection()){

            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Long id = Long.parseLong(rs.getString("req_id"));
                Long roomId = rs.getLong("room_id");
                if(rs.wasNull()){
                    roomId = null;
                }
                Long clientID = Long.parseLong(rs.getString("client_id"));
                RoomType type = RoomType.valueOf(rs.getString("room_type").toUpperCase());
                Timestamp reqDate = rs.getTimestamp("req_date");
                Date start = rs.getDate("start_date");
                Date end = rs.getDate("end_date");
                int persons = rs.getInt("persons_count");
                StatusRequest status = StatusRequest.valueOf(rs.getString("status").toUpperCase());

                Request request = new Request();
                request.setRequestID(id);
                request.setRoomID(roomId);
                request.setClientID(clientID);
                request.setRoomType(type);
                request.setRequestDate(reqDate);
                request.setStartDate(start);
                request.setEndDate(end);
                request.setPersonsCount(persons);
                request.setStatusRequest(status);

                requests.add(request);
            }
            statement.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return requests;
    }


    public Map<Long, Integer> countClientRequest() {
        Map<Long, Integer> reqCount = new HashMap<>();
        String selectTableSQL = "SELECT client_id, count(client_id) AS req_count FROM requests WHERE status != 'cancel'" +
                "GROUP BY (client_id) HAVING req_count >= 1";
        ResultSet rs;
        try (Connection connection = ConnectorDB.getConnection()) {

            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Long clientId = rs.getLong("client_id");
                int count = rs.getInt("req_count");

                reqCount.put(clientId, count);
            }
            statement.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return reqCount;
    }


}
