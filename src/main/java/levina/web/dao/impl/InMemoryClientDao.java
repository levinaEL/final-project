package levina.web.dao.impl;

import levina.web.constants.IClientConstants;
import levina.web.dao.ClientDao;
import levina.web.dao.database.DBConnectionPool;
import levina.web.model.Client;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * InMemoryClientDao implementation of ClientDao
 */
public class InMemoryClientDao implements ClientDao {
    /**
     * number of records from select
     */
    private int noOfRecords;

    public DBConnectionPool dbConnectionPool;

    public static Logger logger = Logger.getLogger(InMemoryClientDao.class);

    private static final String SELECT_CLIENT_BY_ID = "SELECT * FROM clients WHERE client_id = ?";
    private static final String SELECT_CLIENT_BY_USER_ID = "SELECT * FROM clients WHERE user_id = ?";
    private static final String SELECT_CLIENT_BY_EMAIL = "SELECT * FROM clients WHERE email = ?";
    private static final String UPDATE_BAN_CLIENT = "update clients set is_banned = 1 where client_id = ?";
    private static final String INSERT_CLIENT = "insert into clients " +
            "(user_id, last_name, first_name, patronymic_name, email, pasp_series, pasp_number, pasp_prsl_number, " +
            "birthday, address, telephone, is_banned) " +
            "values(?, ?,?,?,?,?,?,?,?,?,?, false)";
    private static final String UPDATE_CLIENT = "update clients " +
            "set user_id = ?, " +
            "last_name = ?," +
            "first_name = ?, " +
            "patronymic_name = ?, " +
            "email = ?, " +
            "pasp_series = ?," +
            "pasp_number = ?, " +
            "pasp_prsl_number= ?, " +
            "birthday = ?, address = ?, " +
            "telephone = ?, " +
            "is_banned = ? " +
            "where client_id = ?";


    private InMemoryClientDao() throws Exception {
        dbConnectionPool = DBConnectionPool.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private ClientDao instance;
        private Exception exception;

        Singleton() {
            try {
                instance = new InMemoryClientDao();
            } catch (Exception e) {
                instance = null;
                exception = e;
            }
        }
    }

    public static ClientDao getInstance() throws Exception {
        if (Singleton.INSTANCE.instance == null) {
            throw Singleton.INSTANCE.exception;
        }
        return Singleton.INSTANCE.instance;
    }

    /**
     * Extract data from ResultSet and set it to Client object
     *
     * @param rs {ResultSet}
     * @return Client
     * @throws SQLException on failing extraction
     */
    private Client extractClientFromResultSet(ResultSet rs) throws SQLException {
        Client client = new Client();

        Long userID = rs.getLong(IClientConstants.USER_ID);
        if (rs.wasNull()) {
            userID = null;
        }
        client.setId(rs.getLong(IClientConstants.CLIENT_ID));
        client.setUserID(userID);
        client.setFirstName(rs.getString(IClientConstants.FIRST_NAME).trim());
        client.setPatronymicName(rs.getString(IClientConstants.PATRONYMIC).trim());
        client.setLastName(rs.getString(IClientConstants.LAST_NAME).trim());
        client.setEmail(rs.getString(IClientConstants.EMAIL).trim());
        client.setPassportSeries(rs.getString(IClientConstants.PASSPORT_SERIES).trim());
        client.setPassportNumber(rs.getInt(IClientConstants.PASSPORT_NUMBER));
        client.setPersonalNumber(rs.getString(IClientConstants.PERSONAL_NUMBER).trim());
        client.setAddress(rs.getString(IClientConstants.ADDRESS).trim());
        client.setBirthday(rs.getString(IClientConstants.BIRTHDAY).trim());
        client.setPhoneNumber(rs.getString(IClientConstants.PHONE).trim());
        client.setBan(rs.getBoolean(IClientConstants.BAN));

        return client;
    }

    /**
     * Forms preparedStatement based on client information for the next execution
     *
     * @param client            -
     * @param preparedStatement {PreparedStatement}
     * @throws SQLException
     */
    private void prepareExecuteStatement(Client client, PreparedStatement preparedStatement) throws SQLException {
        if (client.getUserID() != null) {

            preparedStatement.setLong(1, client.getUserID());
        } else {
            preparedStatement.setNull(1, Types.INTEGER);
        }
        preparedStatement.setString(2, client.getLastName());
        preparedStatement.setString(3, client.getFirstName());
        preparedStatement.setString(4, client.getPatronymicName());
        preparedStatement.setString(5, client.getEmail());
        preparedStatement.setString(6, client.getPassportSeries());
        preparedStatement.setInt(7, client.getPassportNumber());
        preparedStatement.setString(8, client.getPersonalNumber());
        preparedStatement.setString(9, client.getBirthday());
        preparedStatement.setString(10, client.getAddress());
        preparedStatement.setString(11, client.getPhoneNumber());
    }

    /**
     * @param id - clients id
     * @return Client
     */
    @Override
    public Client getById(Long id) {
        ResultSet rs;
        Client client = null;
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_ID);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                client = extractClientFromResultSet(rs);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException e) {
            logger.error("SQL exception in getting client by id", e);
        } catch (Exception e) {
            logger.error("Exception in getting client by id", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return client;
    }

    /**
     * @param id - users id
     * @return Client
     */
    @Override
    public Client getByUserId(Long id) {
        ResultSet rs;
        Client client = null;
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_USER_ID);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                client = extractClientFromResultSet(rs);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException e) {
            logger.error("SQL exception in getting client by user id", e);
        } catch (Exception e) {
            logger.error("Exception in getting client by user id", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return client;
    }

    /**
     * @param email - clients email
     * @return Client
     */
    @Override
    public Client getClientByEmail(String email) {
        ResultSet rs;
        Client client = null;
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_EMAIL);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                client = extractClientFromResultSet(rs);
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException e) {
            logger.error("SQL exception in getting client by email", e);
        } catch (Exception e) {
            logger.error("Exception in getting client by email", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return client;
    }

    /**
     * Insert record into clients table
     *
     * @param client - object that need to be save
     */
    @Override
    public void save(Client client) {
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT);
            prepareExecuteStatement(client, preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            logger.error("SQL exception in saving client", e);
        } catch (Exception e) {
            logger.error("Exception in saving client", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
    }

    /**
     * Update record in clients table
     *
     * @param client - object that need to be updated
     */
    @Override
    public void update(Client client) {
        Long id = client.getId();
        boolean ban = client.isBan();
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT);
            prepareExecuteStatement(client, preparedStatement);

            preparedStatement.setBoolean(12, ban);
            preparedStatement.setLong(13, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            logger.error("SQL exception in updating client", e);
        } catch (Exception e) {
            logger.error("Exception in updating client", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
    }

    /**
     * Get all records from clients table
     *
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection of clients
     */
    @Override
    public Collection<Client> getAll(int offset, int noOfRecords) {
        Collection<Client> clients = new ArrayList<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM clients " +
                "LIMIT  " + offset + ", " + noOfRecords;
        Connection connection = null;
        ResultSet rs;
        try {
            connection = dbConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Client client = extractClientFromResultSet(rs);
                clients.add(client);
            }

            rs = statement.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }

            statement.close();
            rs.close();

        } catch (SQLException e) {
            logger.error("SQL exception in getting all clients", e);
        } catch (Exception e) {
            logger.error("Exception in getting all clients", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return clients;
    }

    /**
     * Get all records from clients table ordered by last name(first_name)
     *
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection of clients
     */
    @Override
    public Collection<Client> getSortedAll(int offset, int noOfRecords) {
        Collection<Client> clients = new ArrayList<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM clients ORDER BY last_name, first_name " +
                "LIMIT  " + offset + ", " + noOfRecords;
        Connection connection = null;
        ResultSet rs;
        try {
            connection = dbConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Client client = extractClientFromResultSet(rs);
                clients.add(client);
            }

            rs = statement.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }
            statement.close();
            rs.close();
        } catch (SQLException e) {
            logger.error("SQL exception in getting all clients", e);
        } catch (Exception e) {
            logger.error("Exception in getting all clients", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
        return clients;
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
     * Update table clients by setting is_ban attribute in true
     *
     * @param client - defines what row should be updated
     */
    public void banClient(Client client) {
        Long id = client.getId();
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BAN_CLIENT);

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            logger.error("SQL exception in banning client", e);
        } catch (Exception e) {
            logger.error("Exception in banning client", e);
        } finally {
            if (connection != null) {
                dbConnectionPool.freeConnection(connection);
            }
        }
    }
}
