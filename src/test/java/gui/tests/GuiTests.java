package gui.tests;

import GUI.ProductEntry;
import GUI.ProductReport;
import GUI.helpers.SimpleListModel;
import dao.ManageProductData;
import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GuiTests {

    private ManageProductData dao;
    private DialogFixture fixture;
    private Robot robot;

    private Product prodOne;
    private Product prodTwo;

    @Before
    public void setUp() {

        this.prodOne = new Product(1, "name1", "cat1", "desc1",
                new BigDecimal("11.00"), new BigDecimal("22.00"));
        this.prodTwo = new Product(2, "name2", "cat2", "desc2",
                new BigDecimal("33.00"), new BigDecimal("44.00"));

        robot = BasicRobot.robotWithNewAwtHierarchy();

        // Slow down the robot a little bit - default is 30 (milliseconds).
        // Do NOT make it less than 5 or you will have thread-race problems.
        robot.settings().delayBetweenEvents(5);

        // add some products for testing with
        Collection<String> categories = new HashSet<>();
        categories.add("Fruit");
        categories.add("Electronics");

        // create a mock for the DAO
        dao = mock(ManageProductData.class);

        Collection<Product> products = new HashSet<>();
        products.add(prodOne);
        products.add(prodTwo);
        
        // stub the getMajors method to return the test majors
        when(dao.getCategories()).thenReturn(categories);
        when(dao.getProducts()).thenReturn(products);
    }

    @After
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }

    @Test
    public void testEdit() {
        // a student to edit
        Product apple = new Product(11, "Apple", "sdfghj", "Fruit", new BigDecimal(11.00), new BigDecimal(11.00));

        // create dialog passing in student and mocked DAO
        ProductEntry dialog = new ProductEntry(null, true, apple, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);

        // show the dialog on the screen, and ensure it is visible
        fixture.show().requireVisible();

        // verify that the UI componenents contains the student's details
        fixture.textBox("txtID").requireText("11");
        fixture.textBox("txtName").requireText("Apple");
        fixture.textBox("txtDescription").requireText("sdfghj");
        fixture.comboBox("cmbCategory").requireSelection("Fruit");
        fixture.textBox("txtPrice").requireText("11.00");
        fixture.textBox("txtQuantity").requireText("11.00");

        // edit the name and major
        fixture.textBox("txtName").selectAll().deleteText().enterText("Pear");
        fixture.textBox("txtDescription").selectAll().deleteText().enterText("new fruit");
        fixture.comboBox("cmbCategory").selectItem("Electronics");
        fixture.textBox("txtPrice").selectAll().deleteText().enterText("2");
        fixture.textBox("txtQuantity").selectAll().deleteText().enterText("5");

        // click the save button
        fixture.button("btnSave").click();

        // create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        // verify that the DAO.save method was called, and capture the passed student
        verify(dao).save(argument.capture());

        // retrieve the passed student from the captor
        Product editedProduct = argument.getValue();

        // add more asserts
        // check that the changes were saved
        assertEquals("Ensure the name was changed", "Pear", editedProduct.getName());
        assertEquals("Ensure the category was changed", "Electronics", editedProduct.getCategory());
    }

    @Test
    public void testSave() {
        // create the dialog passing in the mocked DAO
        ProductEntry dialog = new ProductEntry(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // enter some details into the UI components
//		fixture.textBox("txtId").enterText("1234");
//		fixture.textBox("txtName").enterText("Jack");
//		fixture.comboBox("cmbMajor").selectItem("Knitting");
        fixture.textBox("txtID").enterText("11");
        fixture.textBox("txtName").enterText("Apple");
        fixture.textBox("txtDescription").enterText("sdfghj");
        fixture.comboBox("cmbCategory").selectItem("Fruit");
        fixture.textBox("txtPrice").enterText("11.01");
        fixture.textBox("txtQuantity").enterText("11.01");

        // click the save button
        fixture.button("btnSave").click();

        // create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        // verify that the DAO.save method was called, and capture the passed student
        verify(dao).save(argument.capture());

        // retrieve the passed student from the captor
        Product savedProduct = argument.getValue();

        // test that the student's details were properly saved
        assertEquals("Ensure the ID was saved", "11", savedProduct.getProductID());
        assertEquals("Ensure the name was saved", "Apple", savedProduct.getName());
        assertEquals("Ensure the description was saved", "sdfghj", savedProduct.getDescription());
        assertEquals("Ensure the category was saved", "Fruit", savedProduct.getCategory());
        assertEquals("Ensure the price was saved", new BigDecimal("11.01"), savedProduct.getListPrice());
        assertEquals("Ensure the quantity was saved", new BigDecimal("11.01"), savedProduct.getQuantityInStock());
    }

    @Test
    public void testReportDisplay() {

        ProductReport dialog = new ProductReport(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        verify(dao).getProducts();
        
        // get the model
        SimpleListModel model = (SimpleListModel) fixture.list("listProducts").target().getModel();

        // check the contents
        assertTrue("list contains the expected product", model.contains(prodOne));
        assertTrue("list contains the expected product", model.contains(prodTwo));
        assertEquals("list contains the correct number of products", 2, model.getSize());

    }

}
