/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManageProductsDAO implements ManageProductData {

    private String URL = "jdbc:h2:tcp://localhost:9020/project;IFEXISTS=TRUE";

    public ManageProductsDAO() {
    }

    public ManageProductsDAO(String URL) {
        this.URL = URL;
    }

    @Override
    public void delete(Product product) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "delete from Product where ProductID = ?";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            // copy the data from the student domain object into the SQL parameters
            stmt.setInt(1, product.getProductID());

            // execute the query
            int rs = stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }

    }

    @Override
    public Collection<Product> filter(String cat) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "select * from Product where category = ? ";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            stmt.setString(1, cat);

            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            List<Product> products = new ArrayList<>();

            // query only returns a single result, so use 'if' instead of 'while'
            while (rs.next()) {
                Integer productID = rs.getInt("productID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal listPrice = rs.getBigDecimal("listPrice");
                BigDecimal quantityInStock = rs.getBigDecimal("quantityInStock");

                Product p = new Product(productID, name, description, category, listPrice, quantityInStock);

                products.add(p);
            }

            return products;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }

    }

    @Override
    public Collection<String> getCategories() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "select distinct Category from Product order by Category";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            List<String> categories = new ArrayList<>();

            // query only returns a single result, so use 'if' instead of 'while'
            while (rs.next()) {

                categories.add(rs.getString("category"));
            }

            return categories;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Product> getProducts() {
        String sql = "select * from Product order by productID";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            List<Product> products = new ArrayList<>();

            // query only returns a single result, so use 'if' instead of 'while'
            while (rs.next()) {
                Integer productID = rs.getInt("productID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal listPrice = rs.getBigDecimal("listPrice");
                BigDecimal quantityInStock = rs.getBigDecimal("quantityInStock");

                Product p = new Product(productID, name, description, category, listPrice, quantityInStock);

                products.add(p);
            }

            return products;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void save(Product product) {
        String sql = "merge into Product (PRODUCTID, NAME, DESCRIPTION, CATEGORY, LISTPRICE, QUANTITYINSTOCK) values (?,?,?,?,?,?)";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // copy the data from the student domain object into the SQL parameters
            stmt.setInt(1, product.getProductID());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setBigDecimal(5, product.getListPrice());
            stmt.setBigDecimal(6, product.getQuantityInStock());

            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }

//        System.out.println(this.getCategories());
    }

    @Override
    public Product search(Integer productID) {
        String sql = "select * from Product where ProductID = ?";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            // set the parameter
            stmt.setInt(1, productID);

            // execute the query
            ResultSet rs = stmt.executeQuery();

            // query only returns a single result, so use 'if' instead of 'while'
            if (rs.next()) {
                Integer pID = rs.getInt("productID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal listPrice = rs.getBigDecimal("listPrice");
                BigDecimal quantityInStock = rs.getBigDecimal("quantityInStock");

                return new Product(productID, name, description, category, listPrice, quantityInStock);

            } else {
                // no student matches given ID so return null
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

}
