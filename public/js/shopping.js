"use strict";
class SaleItem {

    constructor(product, quantity) {
        // only set the fields if we have a valid product
        if (product) {
            this.product = product;
            this.quantityPurchased = quantity;
            this.salePrice = product.listPrice;
        }
    }

    getItemTotal() {
        return this.salePrice * this.quantityPurchased;
    }

}

class ShoppingCart {

    constructor() {
        this.items = new Array();
    }

    reconstruct(sessionData) {
        for (let item of sessionData.items) {
            this.addItem(Object.assign(new SaleItem(), item));
        }
    }

    getItems() {
        return this.items;
    }

    addItem(item) {
        this.items.push(item);
    }

    setCustomer(customer) {
        this.customer = customer;
    }

    getTotal() {
        let total = 0;
        for (let item of this.items) {
            total += item.getItemTotal();
        }
        return total;
    }

}


// create a new module, and load the other pluggable modules
var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.factory('productDAO', function ($resource) {
    return $resource('/api/products/:id');
});

module.factory('categoryDAO', function ($resource) {
    return $resource('/api/categories/:cat');
});

module.factory('registerDAO', function ($resource) {
    return $resource('/api/register');
});

module.factory('signInDAO', function ($resource) {
    return $resource('/api/customers/:username');

});

module.factory('saleDAO', function ($resource) {
    return $resource('/api/sales');
});

module.factory('cart', function ($sessionStorage) {
    let cart = new ShoppingCart();

    // is the cart in the session storage?
    if ($sessionStorage.cart) {

        // reconstruct the cart from the session data
        cart.reconstruct($sessionStorage.cart);
    }

    return cart;
});

// viewProducts page controller
module.controller('ProductController', function (productDAO, categoryDAO) {

// load the products
    this.products = productDAO.query();

// load the categories
    this.categories = categoryDAO.query();

// click handler for the category filter buttons
    this.selectCategory = function (selectedCat) {
        this.products = categoryDAO.query({"cat": selectedCat});
    };

// get all of the products
    this.selectAll = function (selectedAll) {
        this.products = productDAO.query();
    };


});

// Customer controller - createAccount page, signIn page
module.controller('CustomerController', function (registerDAO, signInDAO, $sessionStorage, $window) {

    this.registerCustomer = function (customer) {
        registerDAO.save(null, customer);
        console.log(customer);

        // redirect to home
        $window.location.href = 'signIn.html';
    };

    this.signInMessage = "Please sign in to continue.";

    // alias 'this' so that we can access it inside callback functions
    let ctrl = this;

    this.signIn = function (username, password) {

        // get customer from web service
        signInDAO.get({'username': username},
                // success
                        function (customer) {
                            // also store the retrieved customer
                            $sessionStorage.customer = customer;

                            // redirect to home
                            $window.location.href = '.';
                        },
                        // fail
                                function () {
                                    ctrl.signInMessage = 'Sign in failed. Please try again.';
                                }
                        );
                    };

            this.checkSignIn = function () {

                if ($sessionStorage.customer) {
                    // customer exists in session storage
                    this.signedIn = true;
                    this.welcome = "Welcome, " + $sessionStorage.customer.firstName + " " + $sessionStorage.customer.surname;
                } else {
                    this.signedIn = false;
                }


            };

            this.signOut = function () {
                // delete customer from session storage 
                delete $sessionStorage.customer;
                
                // redirect to home page
                $window.location.href = '.';

            };

        });

module.controller('ShoppingCartController', function (cart, $sessionStorage, $window, saleDAO) {
    this.items = cart.getItems();
    this.total = cart.getTotal();
    this.selectedProduct = $sessionStorage.selectedProduct;

    this.buyProduct = function (product) {

        //store the retrieved product
        $sessionStorage.selectedProduct = product;

        // redirect to quantity to purchase page
        $window.location.href = 'quantityToPurchase.html';
    };

    this.addToCart = function (quantity) {

//        console.log("Add to cart called");

        // create instance of saleItem class using product and quantity to initalize
        let saleItem = new SaleItem(this.selectedProduct, quantity);

        // call addItem function
        cart.addItem(saleItem);

        // store cart object in session storage
        $sessionStorage.cart = cart;

        // redirect to quantity to purchase page
        $window.location.href = 'viewProducts.html';


    };

    this.checkOutCart = function () {

//        let shoppingCart = new ShoppingCart();

        // get customer object from session storage
        cart.setCustomer($sessionStorage.customer);

        // call save function
        saleDAO.save(cart);

        // delete cart from session storage 
        delete $sessionStorage.cart;

        // redirect to thank you page
        $window.location.href = 'confirmation.html';

    };


});