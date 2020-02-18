
import GUI.MainMenu;
import dao.ManageProductsDAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Administration {

    private static final ManageProductsDAO productDAO = new ManageProductsDAO();

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(productDAO);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainMenu.setVisible(true);
            }
        });
    }
}
