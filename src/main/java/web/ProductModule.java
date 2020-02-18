/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.ManageProductData;
import org.jooby.Jooby;


public class ProductModule extends Jooby {

    public ProductModule(ManageProductData productDao) {

        get("/api/products", () -> productDao.getProducts()); // show in sequence diagram

        get("/api/products/:id", (req) -> {
            Integer id = req.param("id").intValue();
            return productDao.search(id);
        });

        get("/api/categories", () -> productDao.getCategories());

        get("/api/categories/:category", (req) -> {
            String category = req.param("category").value();
            return productDao.filter(category);
        });

    }

}
