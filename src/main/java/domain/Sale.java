/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Sale {
    private List<SaleItem> items = new ArrayList<>();
    private Customer customer;
    private Integer saleID;
    private Date date;
    private String status;

    public Sale(Customer customer, Integer saleID, Date date, String status) {
        this.customer = customer;
        this.saleID = saleID;
        this.date = date;
        this.status = status;
    }
    
    public BigDecimal getTotal(){
        
        BigDecimal total = BigDecimal.ZERO;
        
        for (SaleItem item : items) {
            BigDecimal itemTotal = item.getItemTotal();
            total = total.add(itemTotal);
        }
        
        return total;
    }
    
    public void addItem(SaleItem saleItem) {
        this.items.add(saleItem);
    }

    @Override
    public String toString() {
        return "Sale{" + "items=" + items + ", customer=" + customer + ", saleID=" + saleID + ", date=" + date + ", status=" + status + '}';
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getSaleID() {
        return saleID;
    }

    public void setSaleID(Integer saleID) {
        this.saleID = saleID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
}
