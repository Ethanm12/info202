/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


public class Customer {
    
    private Integer customerID;
    private String username;
    private String firstName;
    private String surname;
    private String password;
    private String email;
    private String shippingAddress;
    private String creditCard;

    public Customer() {
    }

    
    
    public Customer(Integer customerID, String username, String firstName, String surname, String password, String email, String shippingAddress, String creditCardDetails) {
        this.customerID = customerID;
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.creditCard = creditCardDetails;
    }

    public Integer getcustomerID() {
        return customerID;
    }

    public void setcustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerID=" + customerID + ", username=" + username + ", firstName=" + firstName + ", surname=" + surname + ", password=" + password + ", email=" + email + ", shippingAddress=" + shippingAddress + ", creditCard=" + creditCard + '}';
    }
    
    
}

