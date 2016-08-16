package levina.web.dao.impl;

import levina.web.dao.RequestDao;
import levina.web.model.Request;
import levina.web.model.enums.StatusRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
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
                Timestamp reqDate = rs.getTimestamp("req_date");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int count = rs.getInt("persons_count");
                StatusRequest status = StatusRequest.valueOf(rs.getString("status"));

                request = new Request();

                request.setClientID(clientID);
                request.setRequestID(requestID);
                request.setRoomID(roomID);
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
                Long clientID = rs.getLong("client_id");
                Long requestID = rs.getLong("req_id");
                Long roomID = rs.getLong("room_id");
                Timestamp reqDate = rs.getTimestamp("req_date");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int count = rs.getInt("persons_count");
                StatusRequest status = StatusRequest.valueOf(rs.getString("status"));

                request = new Request();

                request.setClientID(clientID);
                request.setRequestID(requestID);
                request.setRoomID(roomID);
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
        Timestamp reqDate = request.getRequestDate();
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        int count = request.getPersonsCount();
        StatusRequest status = request.getStatusRequest();

        String insertTableSQL = "insert into requests (client_id, room_id, req_date, start_date, end_date, persons_count, status) " +
                "values(?,?,?,?,?,?,?)";
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
            preparedStatement.setTimestamp(3, reqDate);
            preparedStatement.setDate(4, startDate);
            preparedStatement.setDate(5, endDate);
            preparedStatement.setInt(6, count);
            preparedStatement.setString(7, String.valueOf(status));

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

@Override
    public void delete(Long id) {
        String deleteFromTableSQL = "DELETE FROM requests "
                + "WHERE req_id = ?";
        try (Connection connection = ConnectorDB.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(deleteFromTableSQL);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }



    @Override
    public Collection<Request> getAll() {
        Collection<Request> requests = new ArrayList<Request>();
        String selectTableSQL = "SELECT "
                + "req_id, "
                + "req_date, "
                + "start_date, "
                + "end_date, "
                + "persons_count, "
                + "status, "
                + "from requests";
        ResultSet rs;
        try (Connection connection = ConnectorDB.getConnection()){

            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Long id = Long.parseLong(rs.getString("user_id"));
                Timestamp reqDate = rs.getTimestamp("req_date");
                Date start = rs.getDate("start_date");
                Date end = rs.getDate("end_date");
                int persons = rs.getInt("persons_count");
                StatusRequest status = StatusRequest.valueOf(rs.getString("status"));

                Request request = new Request();
                request.setRequestID(id);

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

    public void updateBookingStatus(Long id) {

        String updateRequest = "update requests set status = ? where req_id = ?";
    }
}
