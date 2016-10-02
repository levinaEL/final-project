package levina.web.dao.impl;

import levina.web.constants.IClientConstants;
import levina.web.constants.IRequestConstants;
import levina.web.dao.RequestDao;
import levina.web.dao.database.DBConnectionPool;
import levina.web.model.Request;
import levina.web.model.enums.RoomType;
import levina.web.model.enums.StatusRequest;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MY on 14.08.2016.
 */
public class InMemoryRequestDao implements RequestDao {
    public static Logger logger = Logger.getLogger(InMemoryRequestDao.class);
    public static volatile InMemoryRequestDao instance = new InMemoryRequestDao();
    public DBConnectionPool dbConnectionPool;
    private int noOfRecords;

    private InMemoryRequestDao() {
    }

    @Override
    public Request getById(Long id) {
        String selectTableSQL = "SELECT * FROM requests WHERE req_id = ? ";
        ResultSet rs;
        Request request = new Request();
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                request.setClientID(rs.getLong(IClientConstants.CLIENT_ID));
                request.setRequestID(rs.getLong(IRequestConstants.REQUEST_ID));
                request.setRoomID(rs.getLong(IRequestConstants.ROOM));
                request.setRoomType(RoomType.valueOf(rs.getString(IRequestConstants.TYPE).toUpperCase()));
                request.setRequestDate(rs.getTimestamp(IRequestConstants.REQUEST_DATE));
                request.setStartDate(rs.getDate(IRequestConstants.START_DATE));
                request.setEndDate(rs.getDate(IRequestConstants.END_DATE));
                request.setPersonsCount(rs.getInt(IRequestConstants.PERSONS_COUNT));
                request.setStatusRequest(StatusRequest.valueOf(rs.getString(IRequestConstants.STATUS).toUpperCase()));
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("SQL Exception in getting request by id: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting request by id: ", e);
        }
        return request;
    }

    @Override
    public Request getByClientId(Long id) {
        String selectTableSQL = "SELECT * FROM requests WHERE client_id = ? ";
        ResultSet rs;
        Request request = new Request();
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                request.setClientID(rs.getLong(IClientConstants.CLIENT_ID));
                request.setRequestID(rs.getLong(IRequestConstants.REQUEST_ID));
                request.setRoomID(rs.getLong(IRequestConstants.ROOM));
                request.setRoomType(RoomType.valueOf(rs.getString(IRequestConstants.TYPE).toUpperCase()));
                request.setRequestDate(rs.getTimestamp(IRequestConstants.REQUEST_DATE));
                request.setStartDate(rs.getDate(IRequestConstants.START_DATE));
                request.setEndDate(rs.getDate(IRequestConstants.END_DATE));
                request.setPersonsCount(rs.getInt(IRequestConstants.PERSONS_COUNT));
                request.setStatusRequest(StatusRequest.valueOf(rs.getString(IRequestConstants.STATUS).toUpperCase()));
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("SQL Exception in getting request by clientId: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting request by clientId: ", e);
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
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            if (clientID != null) {
                preparedStatement.setLong(1, clientID);
            } else {
                preparedStatement.setNull(1, Types.INTEGER);
            }
            if (roomID != null) {
                preparedStatement.setLong(2, roomID);
            } else {
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
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL Exception in saving request: ", e);
        } catch (Exception e) {
            logger.error("Exception in saving request: ", e);
        }
    }

    @Override
    public void cancel(Long id) {
        String deleteFromTableSQL = "UPDATE requests SET status = ? WHERE req_id = ?";
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteFromTableSQL);
            preparedStatement.setString(1, String.valueOf(StatusRequest.CANCEL));
            preparedStatement.setLong(2, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("SQL Exception in canceling request: ", e);
        } catch (Exception e) {
            logger.error("Exception in canceling request: ", e);
        }
    }

    @Override
    public void approve(Long requestId, Long roomId) {
        String deleteFromTableSQL = "UPDATE requests SET status = ?, room_id = ? WHERE req_id = ? ";
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteFromTableSQL);
            preparedStatement.setString(1, String.valueOf(StatusRequest.APPROVED).toUpperCase());
            preparedStatement.setLong(2, roomId);
            preparedStatement.setLong(3, requestId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL Exception in approving request: ", e);
        } catch (Exception e) {
            logger.error("Exception in approving request: ", e);
        }
    }


    @Override
    public Collection<Request> getAdminRequests(int offset, int noOfRecords) {
        Collection<Request> requests = new ArrayList<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM requests " +
                "WHERE status = ? AND start_date >= CURRENT_DATE LIMIT " +
                  offset + ", " + noOfRecords;
        ResultSet rs;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setString(1,String.valueOf(StatusRequest.PENDING) );
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong(IRequestConstants.REQUEST_ID);
                Long clientID = rs.getLong(IClientConstants.CLIENT_ID);
                Long roomId = rs.getLong(IRequestConstants.ROOM);
                if(rs.wasNull()){
                    roomId = null;
                }
                RoomType type = RoomType.valueOf(rs.getString(IRequestConstants.TYPE).toUpperCase());
                Timestamp reqDate = rs.getTimestamp(IRequestConstants.REQUEST_DATE);
                Date start = rs.getDate(IRequestConstants.START_DATE);
                Date end = rs.getDate(IRequestConstants.END_DATE);
                int persons = rs.getInt(IRequestConstants.PERSONS_COUNT);
                StatusRequest status = StatusRequest.valueOf(rs.getString(IRequestConstants.STATUS).toUpperCase());

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

            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }

            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL Exception in getting requests for admin: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting requests for admin: ", e);
        }
        return requests;
    }


    @Override
    public Collection<Request> getAllClientsRequests(Long clientID, int offset, int noOfRecords) {
        Collection<Request> requests = new ArrayList<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM requests " +
                "WHERE client_id = ? AND start_date >= CURRENT_DATE ORDER BY req_date DESC " +
                "LIMIT " + offset + ", " + noOfRecords;

        ResultSet rs;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, clientID);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long reqId = rs.getLong(IRequestConstants.REQUEST_ID);
                Long roomId = rs.getLong(IRequestConstants.ROOM);
                if(rs.wasNull()){
                    roomId = null;
                }
                RoomType type = RoomType.valueOf(rs.getString(IRequestConstants.TYPE).toUpperCase());
                Timestamp reqDate = rs.getTimestamp(IRequestConstants.REQUEST_DATE);
                Date start = rs.getDate(IRequestConstants.START_DATE);
                Date end = rs.getDate(IRequestConstants.END_DATE);
                int persons = rs.getInt(IRequestConstants.PERSONS_COUNT);
                StatusRequest status = StatusRequest.valueOf(rs.getString(IRequestConstants.STATUS).toUpperCase());
                Request request = new Request();
                request.setRequestID(reqId);
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
            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL Exception in getting requests for client: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting requests for client: ", e);
        }
        return requests;
    }

    @Override
    public Collection<Request> getAll(int offset, int noOfRecords) {
        Collection<Request> requests = new ArrayList<>();

        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM requests WHERE status != ? ORDER BY req_date DESC" +
                " LIMIT " + offset + ", " + noOfRecords;
        ResultSet rs;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setString(1, String.valueOf(StatusRequest.PENDING));

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong(IRequestConstants.REQUEST_ID);
                Long clientID = rs.getLong(IClientConstants.CLIENT_ID);
                Long roomId = rs.getLong(IRequestConstants.ROOM);
                if(rs.wasNull()){
                    roomId = null;
                }
                RoomType type = RoomType.valueOf(rs.getString(IRequestConstants.TYPE).toUpperCase());
                Timestamp reqDate = rs.getTimestamp(IRequestConstants.REQUEST_DATE);
                Date start = rs.getDate(IRequestConstants.START_DATE);
                Date end = rs.getDate(IRequestConstants.END_DATE);
                int persons = rs.getInt(IRequestConstants.PERSONS_COUNT);
                StatusRequest status = StatusRequest.valueOf(rs.getString(IRequestConstants.STATUS).toUpperCase());

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

            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }

            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL Exception in getting all requests: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting all requests: ", e);
        }
        return requests;
    }

    @Override
    public int getNoOfRecords(){
        return noOfRecords;
    }


    public Map<Long, Integer> countClientRequest(int offset, int noOfPages) {
        Map<Long, Integer> reqCount = new HashMap<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS client_id, count(client_id) AS req_count FROM requests WHERE status != 'cancel'" +
                "GROUP BY (client_id) HAVING req_count >= 1 LIMIT " + offset + ", " + noOfPages;
        ResultSet rs;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Long clientId = rs.getLong(IClientConstants.CLIENT_ID);
                int count = rs.getInt("req_count");

                reqCount.put(clientId, count);
            }

            rs = statement.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }
            statement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL Exception in counting clients requests: ", e);
        } catch (Exception e) {
            logger.error("Exception in counting clients requests: ", e);
        }

        return reqCount;
    }
}
