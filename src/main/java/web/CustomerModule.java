/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.CustomerInterface;
import domain.Customer;
import domain.Sale;
import org.jooby.Err;
import org.jooby.Jooby;
import org.jooby.Status;


public class CustomerModule extends Jooby {

    public CustomerModule(CustomerInterface customerDao) {

        get("/api/customers/:username", (req) -> {
            String id = req.param("username").value();
            Customer customer = customerDao.getCustomer(id);
            if (customer == null){
                throw new Err(Status.NOT_FOUND);
            } else {
                return customer;
            }

        });

        post("/api/register", (req, rsp) -> {
            Customer customer = req.body().to(Customer.class);
            customerDao.save(customer);
            rsp.status(Status.CREATED);
        });
        

    }

}
