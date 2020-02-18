/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageCustomerJdbc implements CustomerInterface {

    private String URL = "jdbc:h2:tcp://localhost:9020/project;IFEXISTS=TRUE";

    public ManageCustomerJdbc() {
    }

    public ManageCustomerJdbc(String URL) {
        this.URL = URL;
    }

    @Override
    public void save(Customer customer) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "insert into Customer(USERNAME, FIRSTNAME, SURNAME, PASSWORD, EMAIL, SHIPPINGADDRESS, CREDITCARD) values (?,?,?,?,?,?,?)";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // copy the data from the student domain object into the SQL parameters
//            stmt.setString(1, customer.getcustomerID());
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getSurname());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getShippingAddress());
            stmt.setString(7, customer.getCreditCard());

            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Customer getCustomer(String username) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "select * from Customer where username = ?";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // set the parameter
            stmt.setString(1, username);
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // query only returns a single result, so use 'if' instead of 'while'
            if (rs.next()) {
                Integer customerID = rs.getInt("customerID");
//                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String surname = rs.getString("surname");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String shippingAddress = rs.getString("shippingAddress");
                String creditCard = rs.getString("creditCard");

                return new Customer(customerID, username, firstName, surname, password, email, shippingAddress, creditCard);
            } else {
                // no student matches given ID so return null
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }

    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        String sql = "select * from Customer where username = ? and password = ?";
        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            // execute the query
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
            

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }

    }

}
