/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.ManageProductData;
import dao.ManageProductsDAO;
import dao.SaleDAO;
import domain.Product;
import domain.Sale;
import domain.SaleItem;
import java.util.Collection;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jooby.Jooby;
import org.jooby.Status;


public class SaleModule extends Jooby {

private ManageProductData productDAO = new ManageProductsDAO();
    
    public SaleModule(SaleDAO saleDAO) {

        post("/api/sales", (req, rsp) -> {
            Sale sale = req.body().to(Sale.class);
            System.out.println(sale);
            saleDAO.save(sale);
            
        

            try {

//                Product realProduct = productDAO.search(products.getProducts());
                
                StringBuilder bob = new StringBuilder();

                bob.append("Dear " + sale.getCustomer().getFirstName() + ",");
                bob.append("\n\nThank you for your order. We hope you had a good time shopping with us."
                        + "\nYour product details are displayed below.");

                for (SaleItem item : sale.getItems()) {
                    bob.append("\n\n" + item.getProduct().getName());
                    bob.append("\nPrice: $" + item.getSalePrice());
                    bob.append("\nQuantity: " + item.getQuantityPurchased());
                    bob.append("\nItem Total: $" + item.getItemTotal());
                }
                
                bob.append("\n\nGrand Total: $" + sale.getTotal());
                bob.append("\n\nThank you for shopping with Whittaker's");
                
                
                String msg = bob.toString();
                
                Email email = new SimpleEmail();
                email.setHostName("localhost");
                email.setSmtpPort(2525);
                email.setFrom("customer.service@gmail.com");
                email.setSubject("Order Confirmation");
                email.setMsg(msg);
                email.addTo(sale.getCustomer().getEmail());
                email.send();
                
            } catch (EmailException e) {
                System.out.println("Test email");
                e.printStackTrace();
            }

            rsp.status(Status.CREATED);

        });

    }

}
