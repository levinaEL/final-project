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
 * InMemoryClientDao implementation of RequestDao
 */
public class InMemoryRequestDao implements RequestDao {
    /**
     * number of records from select
     */
    private int noOfRecords;

    public static Logger logger = Logger.getLogger(InMemoryRequestDao.class);
    public DBConnectionPool dbConnectionPool;

    private static final String SELECT_REQUEST_BY_ID = "SELECT * FROM requests WHERE req_id = ? ";
    private static final String SELECT_REQUEST_BY_CLIENT_ID = "SELECT * FROM requests WHERE client_id = ? ";
    private static final String UPDATE_REQUEST_CANCEL = "UPDATE requests SET status = ? WHERE req_id = ?";
    private static final String UPDATE_REQUEST_APPROVE = "UPDATE requests SET status = ?, room_id = ? WHERE req_id = ? ";
    private static final String INSERT_REQUEST = "INSERT INTO requests (client_id, room_id, room_type, req_date, start_date, end_date, persons_count, status) VALUES (?,?,?,?,?,?,?,?)";

    private InMemoryRequestDao() throws Exception {
        dbConnectionPool = DBConnectionPool.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private RequestDao instance;
        private Exception exception;

        Singleton() {
            try {
                instance = new InMemoryRequestDao();
            } catch (Exception e) {
                instance = null;
                exception = e;
            }
        }
    }

    public static RequestDao getInstance() throws Exception {
        if (Singleton.INSTANCE.instance == null) {
            throw Singleton.INSTANCE.exception;
        }
        return Singleton.INSTANCE.instance;
    }

    /**
     * Extract data from ResultSet and set it to Request object
     *
     * @param rs {ResultSet}
     * @return Request
     * @throws SQLException on failing extraction
     */
    private Request extractRequestFromResultSet(ResultSet rs) throws SQLException {
        Request request = new Request();

        request.setClientID(rs.getLong(IClientConstants.CLIENT_ID));
        request.setRequestID(rs.getLong(IRequestConstants.REQUEST_ID));
        Long roomId = rs.getLong(IRequestConstants.ROOM);
        if (rs.wasNull()) {
            roomId = null;
        }
        request.setRoomID(roomId);
        request.setRoomType(RoomType.valueOf(rs.getString(IRequestConstants.TYPE).toUpperCase()));
        request.setRequestDate(rs.getTimestamp(IRequestConstants.REQUEST_DATE));
        request.setStartDate(rs.getDate(IRequestConstants.START_DATE));
        request.setEndDate(rs.getDate(IRequestConstants.END_DATE));
        request.setPersonsCount(rs.getInt(IRequestConstants.PERSONS_COUNT));
        request.setStatusRequest(StatusRequest.valueOf(rs.getString(IRequestConstants.STATUS).toUpperCase()));

        return request;
    }

    /**
     * Forms preparedStatement based on request information for the next execution
     *
     * @param request           -
     * @param preparedStatement {PreparedStatement}
     * @throws SQLException
     */
    private void prepareExecuteStatement(Request request, PreparedStatement preparedStatement) throws SQLException {

        if (request.getClientID() != null) {
            preparedStatement.setLong(1, request.getClientID());
        } else {
            preparedStatement.setNull(1, Types.INTEGER);
        }
        if (request.getRoomID() != null) {
            preparedStatement.setLong(2, request.getRoomID());
        } else {
            preparedStatement.setNull(2, Types.INTEGER);
        }
        preparedStatement.setString(3, String.valueOf(request.getRoomType()).toUpperCase());
        preparedStatement.setTimestamp(4, request.getRequestDate());
        preparedStatement.setDate(5, request.getStartDate());
        preparedStatement.setDate(6, request.getEndDate());
        preparedStatement.setInt(7, request.getPersonsCount());
        preparedStatement.setString(8, String.valueOf(request.getStatusRequest()).toUpperCase());
    }

    /**
     * @param id - request id
     * @return Request
     */
    @Override
    public Request getById(Long id) {

        ResultSet rs;
        Connection connection = null;
        Request request = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REQUEST_BY_ID);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                request = extractRequestFromResultSet(rs);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException e) {
            logger.error("SQL Exception in getting request by id: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting request by id: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return request;
    }

    /**
     * @param id - clients id
     * @return Request
     */
    @Override
    public Request getByClientId(Long id) {

        ResultSet rs;
        Request request = new Request();
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REQUEST_BY_CLIENT_ID);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                request = extractRequestFromResultSet(rs);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException e) {
            logger.error("SQL Exception in getting request by clientId: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting request by clientId: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return request;
    }

    /**
     * Insert record into requests table
     *
     * @param request - object that need to be save
     */
    @Override
    public void save(Request request) {
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REQUEST);

            prepareExecuteStatement(request, preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            logger.error("SQL Exception in saving request: ", e);
        } catch (Exception e) {
            logger.error("Exception in saving request: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
    }

    /**
     * Update table requests by setting status attribute in CANCEL
     *
     * @param id - defines what row should be updated
     */
    @Override
    public void cancel(Long id) {
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REQUEST_CANCEL);
            preparedStatement.setString(1, String.valueOf(StatusRequest.CANCEL));
            preparedStatement.setLong(2, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error("SQL Exception in canceling request: ", e);
        } catch (Exception e) {
            logger.error("Exception in canceling request: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
    }

    /**
     * Update table requests by setting status attribute in APPROVE
     *
     * @param requestId - defines what row should be updated
     * @param roomId    - defines what value should be set
     */
    @Override
    public void approve(Long requestId, Long roomId) {
        Connection connection = null;

        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REQUEST_APPROVE);
            preparedStatement.setString(1, String.valueOf(StatusRequest.APPROVED).toUpperCase());
            preparedStatement.setLong(2, roomId);
            preparedStatement.setLong(3, requestId);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            logger.error("SQL Exception in approving request: ", e);
        } catch (Exception e) {
            logger.error("Exception in approving request: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
    }

    /**
     * Get all records that should be process by admin from requests table
     *
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection of requests
     */
    @Override
    public Collection<Request> getAdminRequests(int offset, int noOfRecords) {
        Collection<Request> requests = new ArrayList<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM requests " +
                "WHERE status = ? AND start_date >= CURRENT_DATE LIMIT " +
                offset + ", " + noOfRecords;
        Connection connection = null;
        ResultSet rs;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);

            preparedStatement.setString(1, String.valueOf(StatusRequest.PENDING));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                requests.add(extractRequestFromResultSet(rs));
            }

            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }

            preparedStatement.close();
            rs.close();

        } catch (SQLException e) {
            logger.error("SQL Exception in getting requests for admin: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting requests for admin: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return requests;
    }


    /**
     * Get all clients records from requests table ordered by req_date
     *
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection of requests
     */
    @Override
    public Collection<Request> getAllClientsRequests(Long clientID, int offset, int noOfRecords) {
        Collection<Request> requests = new ArrayList<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM requests " +
                "WHERE client_id = ? AND start_date >= CURRENT_DATE ORDER BY req_date DESC " +
                "LIMIT " + offset + ", " + noOfRecords;
        Connection connection = null;
        ResultSet rs;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, clientID);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                requests.add(extractRequestFromResultSet(rs));
            }
            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException e) {
            logger.error("SQL Exception in getting requests for client: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting requests for client: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return requests;
    }

    /**
     * Get all records from requests table ordered by req_date
     *
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection of requests
     */
    @Override
    public Collection<Request> getAll(int offset, int noOfRecords) {
        Collection<Request> requests = new ArrayList<>();

        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM requests WHERE status != ? " +
                "ORDER BY req_date DESC" +
                " LIMIT " + offset + ", " + noOfRecords;
        Connection connection = null;
        ResultSet rs;
        try {

            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setString(1, String.valueOf(StatusRequest.PENDING));

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Request request = extractRequestFromResultSet(rs);
                RoomType type = RoomType.valueOf(rs.getString(IRequestConstants.TYPE).toUpperCase());
                request.setRoomType(type);

                requests.add(request);
            }

            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }

            preparedStatement.close();
            rs.close();
        } catch (SQLException e) {
            logger.error("SQL Exception in getting all requests: ", e);
        } catch (Exception e) {
            logger.error("Exception in getting all requests: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return requests;
    }

    /**
     * Get number of records from select result
     *
     * @return int - number of records
     */
    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }


    /**
     * Count for each client number of requests, that he make without cancel ones
     *
     * @param offset    - use for pagination, number of start record on the page
     * @param noOfPages - use for pagination, number of records per page
     * @return Map: key - id, value - count
     */
    public Map<Long, Integer> countClientRequest(int offset, int noOfPages) {
        Map<Long, Integer> reqCount = new HashMap<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS client_id, count(client_id) AS req_count FROM requests WHERE status != 'cancel'" +
                "GROUP BY (client_id) HAVING req_count >= 1 LIMIT " + offset + ", " + noOfPages;
        ResultSet rs;
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Long clientId = rs.getLong(IClientConstants.CLIENT_ID);
                int count = rs.getInt("req_count");
                reqCount.put(clientId, count);
            }

            rs = statement.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }
            statement.close();
            rs.close();

        } catch (SQLException e) {
            logger.error("SQL Exception in counting clients requests: ", e);
        } catch (Exception e) {
            logger.error("Exception in counting clients requests: ", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }

        return reqCount;
    }
}
