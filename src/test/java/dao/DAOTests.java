/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class DAOTests {

//    private ProductDAO dao = new ProductDAO();
    private ManageProductsDAO dao = new ManageProductsDAO("jdbc:h2:tcp://localhost:9020/project-testing");
    private Product prodOne;
    private Product prodTwo;
    private Product prodThree;

    @Before
    public void setUp() {
        this.prodOne = new Product(1, "name1",  "desc1", "cat1",
                new BigDecimal("11.00"), new BigDecimal("22.00"));
        this.prodTwo = new Product(2, "name2", "desc2", "cat2", 
                new BigDecimal("33.00"), new BigDecimal("44.00"));
        this.prodThree = new Product(3, "name3", "desc3", "cat3", 
                new BigDecimal("55.00"), new BigDecimal("66.00"));
        // save the products
        dao.save(prodOne);
        dao.save(prodTwo);
        // Note: Intentionally not saving prodThree
    }

    @After
    public void tearDown() {
        dao.delete(prodOne);
        dao.delete(prodTwo);
        dao.delete(prodThree);
    }

    @Test
    public void testDaoSave() {
        // save the product using DAO
        dao.save(prodThree);
        // retrieve the same product via DAO
        Product retrieved = dao.search(3);
        // ensure that the product we saved is the one we got back
        assertEquals("Retrieved product should be the same",
                prodThree, retrieved);

        // change prodThree's details (name)
        prodThree.setName("newProdThree");
        dao.save(prodThree);

        // get prodThree again
        Product retrievedAgain = dao.search(3);

        // check that the changes stuck
        assertEquals("Retrieved product name should be the same",
                "newProdThree", retrievedAgain.getName());
    }

    @Test
    public void testDaoDelete() {
        // delete the product via the DAO
        dao.delete(prodOne);
        // try to retrieve the deleted product
        Product retrieved = dao.search(1);
        // ensure that the student was not retrieved (should be null)
        assertNull("Product should no longer exist", retrieved);
    }

    @Test
    public void testDaoGetAll() {
        Collection<Product> products = dao.getProducts();

        // ensure the result includes the two saved products
        assertTrue("prodOne should exist", products.contains(prodOne));
        assertTrue("prodTwo should exist", products.contains(prodTwo));

        // ensure the result ONLY includes the two saved products
        assertEquals("Only 2 products in result", 2, products.size());

        // find prodOne - result is not a map, so we have to scan for it
        for (Product p : products) {
            if (p.equals(prodOne)) {
                // ensure that all of the details were correctly retrieved
                assertEquals(prodOne.getProductID(), p.getProductID());
                assertEquals(prodOne.getName(), p.getName());
                assertEquals(prodOne.getDescription(), p.getDescription());
                assertEquals(prodOne.getCategory(), p.getCategory());
                assertEquals(prodOne.getListPrice(), p.getListPrice());
                assertEquals(prodOne.getQuantityInStock(), p.getQuantityInStock());
            }
        }
    }

    // get products by ID
    @Test
    public void testDaoFindById() {
        // get prodOne using findById method
        Product p = dao.search(1);

        // assert that you got back prodOne, and not another product
        assertEquals("prodOne returned", prodOne, p);

        assertEquals(prodOne.getProductID(), p.getProductID());
        assertEquals(prodOne.getName(), p.getName());
        assertEquals(prodOne.getDescription(), p.getDescription());
        assertEquals(prodOne.getCategory(), p.getCategory());
        assertEquals(prodOne.getListPrice(), p.getListPrice());
        assertEquals(prodOne.getQuantityInStock(), p.getQuantityInStock());

        // assert that prodOne's details were properly retrieved
        // call getById using a non-existent ID
        Product retrieved = dao.search(-1);

        // assert that the result is null
        assertNull("Product should no longer exist", retrieved);

    }

    @Test
    public void testDaoFilterbyCat() {
        Collection<Product> categories = dao.filter("cat1");

        // ensure the result includes the two saved products
        assertTrue("prodOne should exist", categories.contains(prodOne));
        assertFalse("prodTwo should not exist", categories.contains(prodTwo));

        // ensure the result ONLY includes the two saved products
        assertEquals("Only 1 products in result", 1, categories.size());

    }

    @Test
    public void testDaoGetAllCat() {
        Collection<String> categories = dao.getCategories();

        // ensure the result includes the two saved products
        assertTrue("prodOne should exist", categories.contains("cat1"));
        assertTrue("prodTwo should exist", categories.contains("cat2"));

        // ensure the result ONLY includes the two saved products
        assertEquals("Only 2 products in result", 2, categories.size());

    }



}
