
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chini489
 */
public class testEmail {

    public static void main(String[] args) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("localhost");
            email.setSmtpPort(2525);
            email.setFrom("user@gmail.com");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("foo@bar.com");
            email.send();

        } catch (EmailException e) {
            System.out.println("Test email");
            e.printStackTrace();
        }

    }

}
