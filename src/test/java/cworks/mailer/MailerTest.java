package cworks.mailer;

import cworks.mailer.Mail;
import cworks.mailer.Mailer;
import org.junit.Test;

import java.io.File;

public class MailerTest {

    @Test
    public void defaultCreate() {
        // create from environment
        // -----------------------
        // mailer.properties inside jar
        // mailer.properties outside jar in current dir or config dir
        // os env
        // java system properties
        // jndi properties, java:comp/env
        // command line args
        Mailer mailer1 = Mailer.create();
    }

    public void mailerTest() {

        Mailer mailer2 = Mailer.create("smtp.gmail.com")
            .username("me")
            .password("al82ap0")
            .port(25)
            .protocol("smtp").make();

        Mailer mailer3 = Mailer.create("smtp.gmail.com")
            .username("corbett")
            .password("al82ap0").make();

        Mail mail1 = Mailer.create("smtp.gmail.com")
            .username("corbett")
            .password("al82ap0")
            .mail("from")
            .to("to")
            .subject("Welcome")
            .body("Welcome aboard mate!")
            .attachment(new File("giftcard.pdf"))
            .make();

        Mailer.create();

        Mailer.mail("from").to("to").body("Hello").send();
    }
}
