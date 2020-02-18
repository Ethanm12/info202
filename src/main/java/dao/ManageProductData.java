/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Collection;


public interface ManageProductData {

    void delete(Product product);

    Collection<Product> filter(String cat);

    Collection<String> getCategories();

    Collection<Product> getProducts();

    void save(Product product);

    Product search(Integer productID);
    
}
