/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.CustomerInterface;
import dao.ManageCustomerJdbc;
import dao.ManageProductsDAO;
import dao.SaleDAO;
import dao.SaleDbDao;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;


public class Server extends Jooby {

    private ManageProductsDAO productDao = new ManageProductsDAO();
    private CustomerInterface customerDao = new ManageCustomerJdbc();
    private SaleDAO saleDao = new SaleDbDao();

    public Server() {
        port(8030);

        use(new Gzon());
        use(new ProductModule(productDao));
        use(new CustomerModule(customerDao));
        use(new SaleModule(saleDao));
        use(new AssetModule());
    }

    public static void main(String[] args) throws Exception {

        System.out.println("\nStarting Server.");
        Server server = new Server();
        CompletableFuture.runAsync(() -> {
            server.start();
        });
        server.onStarted(() -> {
            System.out.println("\nPress Enter to stop the server.");
        });
        // wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
        

    }
}
