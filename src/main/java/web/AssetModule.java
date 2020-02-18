/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import org.jooby.Jooby;
import org.jooby.Results;


public class AssetModule extends Jooby {

    public AssetModule() {
        assets("/confirmation.html");
        assets("/createAccount.html");
        assets("/index.html");
        assets("/menu.html");
        assets("/quantityToPurchase.html");
        assets("/shoppingCart.html");
        assets("/signIn.html");
        assets("/viewProducts.html");
        assets("/css/website.css");
        assets("js/angular-resource.min.js");
        assets("js/angular.min.js");
        assets("js/ngStorage.min.js");
        assets("js/shopping.js");
        assets("/images/*.png");
        assets("/images/*.jpg");
// make index.html the default page
        assets("/", "index.html");
// prevent 404 errors due to browsers requesting favicons
        get("/favicon.ico", () -> Results.noContent());
    }
}
