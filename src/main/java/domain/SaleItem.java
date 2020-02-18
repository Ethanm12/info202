/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;


public class SaleItem {
    private Sale sale;
    private Product product;
    private BigDecimal quantityPurchased;
    private BigDecimal salePrice;

    public SaleItem() {
    }

    
    
    public SaleItem(Sale sale, Product product, BigDecimal quantityPurchased, BigDecimal salePrice) {
        this.sale = sale;
        this.product = product;
        this.quantityPurchased = quantityPurchased;
        this.salePrice = salePrice;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(BigDecimal quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
    
    public BigDecimal getItemTotal(){
        return salePrice.multiply(quantityPurchased);
    }

    @Override
    public String toString() {
        return "SaleItem{" + "product=" + product + ", quantityPurchased=" + quantityPurchased + ", salePrice=" + salePrice + '}';
    }
           
    
    
}
