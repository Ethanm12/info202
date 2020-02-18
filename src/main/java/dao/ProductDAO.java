/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Product;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class ProductDAO implements ManageProductData {

    private static final Collection<Product> products = new HashSet<>();
    private static final Collection<String> categories = new HashSet<>();
    private static final Map<Integer, Product> searchProduct = new HashMap<>();
    private static final Multimap<String, Product> category = HashMultimap.create();

    public ProductDAO() {

    }

    @Override
    public void save(Product product) {
        products.add(product);
        categories.add(product.getCategory());
        searchProduct.put(product.getProductID(), product);
        category.put(product.getCategory(), product);
    }

    @Override
    public Collection<Product> getProducts() {
        return products;
    }

    @Override
    public Collection<String> getCategories() {
        return categories;
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
        categories.remove(product.getCategory());
        searchProduct.remove(product.getProductID(), product);
        category.remove(product.getCategory(), product);
    }

    @Override
    public Product search(Integer productID) {
        return searchProduct.get(productID);
    }

    @Override
    public Collection<Product> filter(String cat) {
        return category.get(cat);

    }
    
    
}
