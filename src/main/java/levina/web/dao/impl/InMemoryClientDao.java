package levina.web.dao.impl;

import levina.web.dao.ClientDao;
import levina.web.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by MY on 07.08.2016.
 */
public class InMemoryClientDao implements ClientDao {
    private static Logger logger = Logger.getLogger(InMemoryClientDao.class.getName());
    public static volatile InMemoryClientDao instance = new InMemoryClientDao();

    private InMemoryClientDao() {
    }

    @Override
    public Client getById(Long id) {
        String selectTableSQL = "SELECT * FROM clients "
                + "WHERE client_id = ?";
        ResultSet rs;
        Client client = null;
        try (Connection connection = ConnectorDB.getConnection()) {

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
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return client;
    }

    @Override
    public Client getByUserId(Long id) {
        String selectTableSQL = "SELECT * FROM clients WHERE user_id = ?";
        ResultSet rs;
        Client client = null;
        try (Connection connection = ConnectorDB.getConnection()) {

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
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return client;
    }

    @Override
    public Client getClientByEmail(String email) {
        String selectTableSQL = "SELECT * FROM clients WHERE email = ?";
        ResultSet rs;
        Client client = null;
        try (Connection connection = ConnectorDB.getConnection()) {
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
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
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
        try (Connection connection = ConnectorDB.getConnection()) {
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

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
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
            Connection connection = ConnectorDB.getConnection();
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
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public Collection<Client> getAll() {
        Collection<Client> clients = new ArrayList<>();
        String selectTableSQL = "SELECT " +
                "client_id, " +
                "last_name, " +
                "first_name, " +
                "email, " +
                "pasp_series, pasp_number, pasp_prsl_number, " +
                "address, " +
                "telephone, " +
                "is_banned " +
                "FROM clients";

        ResultSet rs;
        try {
            Connection connection = ConnectorDB.getConnection();
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
            statement.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return clients;
    }

    public void banClient(Client client){

        String updateTableSQL =  "update clients set is_banned = 1 where client_id = ?";
        Long id = client.getId();

        try {
            Connection connection = ConnectorDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateTableSQL);

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
