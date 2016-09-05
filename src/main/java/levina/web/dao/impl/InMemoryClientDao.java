package levina.web.dao.impl;

import levina.web.dao.ClientDao;
import levina.web.dao.database.DBConnectionPool;
import levina.web.model.Client;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by MY on 07.08.2016.
 */
public class InMemoryClientDao implements ClientDao {
    public static Logger logger = Logger.getLogger(InMemoryClientDao.class);
    public static volatile InMemoryClientDao instance = new InMemoryClientDao();
    public DBConnectionPool dbConnectionPool;

    private int noOfRecords;


    private InMemoryClientDao() {
    }

    @Override
    public Client getById(Long id) {
        String selectTableSQL = "SELECT * FROM clients WHERE client_id = ?";
        ResultSet rs;
        Client client = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long clientId = rs.getLong("client_id");
                Long userID = rs.getLong("user_id");
                if(rs.wasNull()){
                    userID = null;
                }
                String firstName = rs.getString("first_name").trim();
                String patronName = rs.getString("patronymic_name").trim();
                String lastName = rs.getString("last_name").trim();
                String email = rs.getString("email").trim();
                String pSeries = rs.getString("pasp_series").trim();
                int pNumber = rs.getInt("pasp_number");
                String personalNumb = rs.getString("pasp_prsl_number").trim();
                String address = rs.getString("address").trim();
                String birthday = rs.getString("birthday").trim();
                String phone = rs.getString("telephone").trim();
                boolean ban = rs.getBoolean("is_banned");

                client = new Client();

                client.setId(clientId);
                client.setUserID(userID);
                client.setFirstName(firstName);
                client.setPatronymicName(patronName);
                client.setLastName(lastName);
                client.setEmail(email);
                client.setPassportSeries(pSeries);
                client.setPassportNumber(pNumber);
                client.setPersonalNumber(personalNumb);
                client.setAddress(address);
                client.setBirthday(birthday);
                client.setPhoneNumber(phone);
                client.setBan(ban);
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("SQL exception in getting client by id", e);
        } catch (Exception e) {
            logger.error("Exception in getting client by id", e);e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client getByUserId(Long id) {
        String selectTableSQL = "SELECT * FROM clients WHERE user_id = ?";
        ResultSet rs;
        Client client = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long clientID= rs.getLong("client_id");
                Long userID = rs.getLong("user_id");
                String firstName = rs.getString("first_name").trim();
                String patronName = rs.getString("patronymic_name").trim();
                String lastName = rs.getString("last_name").trim();
                String email = rs.getString("email").trim();
                String pSeries = rs.getString("pasp_series").trim();
                int pNumber = rs.getInt("pasp_number");
                String personalNumb = rs.getString("pasp_prsl_number").trim();
                String address = rs.getString("address").trim();
                String birthday = rs.getString("birthday").trim();
                String phone = rs.getString("telephone").trim();
                boolean ban = rs.getBoolean("is_banned");

                client = new Client();

                client.setId(clientID);
                client.setUserID(userID);
                client.setFirstName(firstName);
                client.setPatronymicName(patronName);
                client.setLastName(lastName);
                client.setEmail(email);
                client.setPassportSeries(pSeries);
                client.setPassportNumber(pNumber);
                client.setPersonalNumber(personalNumb);
                client.setAddress(address);
                client.setBirthday(birthday);
                client.setPhoneNumber(phone);
                client.setBan(ban);
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("SQL exception in getting client by user id", e);
        } catch (Exception e) {
            logger.error("Exception in getting client by user id", e);
        }
        return client;
    }

    @Override
    public Client getClientByEmail(String email) {
        String selectTableSQL = "SELECT * FROM clients WHERE email = ?";
        ResultSet rs;
        Client client = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("client_id");
                Long userID = rs.getLong("user_id");
                if(rs.wasNull()){
                    userID = null;
                }
                String firstName = rs.getString("first_name").trim();
                String patronName = rs.getString("patronymic_name").trim();
                String lastName = rs.getString("last_name").trim();
                String pSeries = rs.getString("pasp_series").trim();
                int pNumber = rs.getInt("pasp_number");
                String personalNumb = rs.getString("pasp_prsl_number").trim();
                String address = rs.getString("address").trim();
                String birthday = rs.getString("birthday").trim();
                String phone = rs.getString("telephone").trim();
                boolean ban = rs.getBoolean("is_banned");

                client = new Client();

                client.setId(id);
                client.setUserID(userID);
                client.setFirstName(firstName);
                client.setPatronymicName(patronName);
                client.setLastName(lastName);
                client.setPassportSeries(pSeries);
                client.setPassportNumber(pNumber);
                client.setPersonalNumber(personalNumb);
                client.setAddress(address);
                client.setBirthday(birthday);
                client.setPhoneNumber(phone);
                client.setBan(ban);
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in getting client by email", e);
        } catch (Exception e) {
            logger.error("Exception in getting client by email", e);
        }
        return client;
    }


    @Override
    public void save(Client client) {
        Long userID = client.getUserID();
        String email = client.getEmail();
        String firstName = client.getFirstName();
        String pName = client.getPatronymicName();
        String lastName = client.getLastName();
        String pSeries = client.getPassportSeries();
        int pNumber = client.getPassportNumber();
        String prslNumber = client.getPersonalNumber();
        String address = client.getAddress();
        String birthday = client.getBirthday();
        String phone = client.getPhoneNumber();


        String insertTableSQL = "insert into clients (user_id, last_name, first_name, patronymic_name, email, " +
                "pasp_series, pasp_number, pasp_prsl_number, birthday, address, telephone, is_banned) " +
                "values(?, ?,?,?,?,?,?,?,?,?,?, false)";
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            if(userID != null) {
                preparedStatement.setLong(1, userID);
            }else{
                preparedStatement.setNull(1, Types.INTEGER);
            }
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, pName);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, pSeries);
            preparedStatement.setInt(7, pNumber);
            preparedStatement.setString(8, prslNumber);
            preparedStatement.setString(9, birthday);
            preparedStatement.setString(10, address);
            preparedStatement.setString(11, phone);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in saving client", e);
        } catch (Exception e) {
            logger.error("Exception in saving client", e);
        }
    }

    @Override
    public void update(Client client) {
        Long id = client.getId();
        Long userID = client.getUserID();
        String email = client.getEmail();
        String firstName = client.getFirstName();
        String pName = client.getPatronymicName();
        String lastName = client.getLastName();
        String pSeries = client.getPassportSeries();
        int pNumber = client.getPassportNumber();
        String prslNumber = client.getPersonalNumber();
        String address = client.getAddress();
        String birthday = client.getBirthday();
        String phone = client.getPhoneNumber();
        boolean ban = client.isBan();

        String updateTableSQL =  "update clients set user_id = ?, last_name = ?, first_name = ?, patronymic_name = ?, email = ?, pasp_series = ?, pasp_number = ?, pasp_prsl_number= ?, birthday = ?, address = ?, telephone = ?, is_banned = ? where client_id = ?";

        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateTableSQL);
            if(userID != null) {
                preparedStatement.setLong(1, userID);
            }else{
                preparedStatement.setNull(1, Types.INTEGER);
            }
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, pName);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, pSeries);
            preparedStatement.setInt(7, pNumber);
            preparedStatement.setString(8, prslNumber);
            preparedStatement.setString(9, birthday);
            preparedStatement.setString(10, address);
            preparedStatement.setString(11, phone);
            preparedStatement.setBoolean(12, ban);

            preparedStatement.setLong(13, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in updating client", e);
        } catch (Exception e) {
            logger.error("Exception in updating client", e);
        }
    }


    @Override
    public Collection<Client> getAll(int offset, int noOfRecords) {
        Collection<Client> clients = new ArrayList<>();
        String selectTableSQL = "SELECT SQL_CALC_FOUND_ROWS * FROM clients LIMIT " +
                 offset + ", " + noOfRecords;

        ResultSet rs;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Long id = rs.getLong("client_id");
                String firstName = rs.getString("first_name").trim();
                String lastName = rs.getString("last_name").trim();
                String email = rs.getString("email").trim();
                String pSeries = rs.getString("pasp_series").trim();
                int pNumber = rs.getInt("pasp_number");
                String prslNumber = rs.getString("pasp_prsl_number").trim();
                String address = rs.getString("address").trim();
                String phone = rs.getString("telephone").trim();
                boolean ban = rs.getBoolean("is_banned");


                Client client = new Client();
                client.setId(id);
                client.setEmail(email);
                client.setFirstName(firstName);
                client.setLastName(lastName);
                client.setPassportSeries(pSeries);
                client.setPassportNumber(pNumber);
                client.setPersonalNumber(prslNumber);
                client.setAddress(address);
                client.setPhoneNumber(phone);
                client.setBan(ban);

                clients.add(client);
            }

            rs = statement.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }

            statement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in getting all clients", e);
        } catch (Exception e) {
            logger.error("Exception in getting all clients", e);
        }
        return clients;
    }

    @Override
    public int getNoOfRecords(){
        return noOfRecords;
    }

    public void banClient(Client client){

        String updateTableSQL =  "update clients set is_banned = 1 where client_id = ?";
        Long id = client.getId();

        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateTableSQL);

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in banning client", e);
        } catch (Exception e) {
            logger.error("Exception in banning client", e);
        }
    }
}
