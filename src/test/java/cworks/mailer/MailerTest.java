package cworks.mailer;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailerTest {

    private static final String BUILD_ROOT = IO.USER_DIR + "/build";

    @Test
    public void testCreateDefault() throws IOException {
        IO.mkDirs(IO.USER_DIR + "/config");
        IO.writeFile(IO.USER_DIR + "/config/mailer.properties",
                "mail.host=smtp.config.com" + IO.NEW_LINE +
                        "mail.user=corbett" + IO.NEW_LINE +
                        "mail.password=wordup");
        Mailer mailer = Mailer.make();
        Assert.assertEquals(mailer.getHost(), "smtp.config.com");
        Assert.assertEquals(mailer.getUser(), "corbett");
        Assert.assertEquals(mailer.getPassword(), "wordup");
        IO.rmDirs(IO.USER_DIR + "/config");

        IO.mkDirs(IO.USER_DIR + "/mailer/properties");
        IO.writeFile(IO.USER_DIR + "/mailer/properties/mailer.properties",
                "mail.host=smtp.mailer.properties.com" + IO.NEW_LINE +
                        "mail.user=corbett" + IO.NEW_LINE +
                        "mail.password=toejam");
        mailer = Mailer.make();
        Assert.assertEquals(mailer.getHost(), "smtp.mailer.properties.com");
        Assert.assertEquals(mailer.getUser(), "corbett");
        Assert.assertEquals(mailer.getPassword(), "toejam");
        IO.rmDirs(IO.USER_DIR + "/mailer");
    }

    @Test
    public void mailerCustomConfig() throws IOException {
        Properties props = IO.asProperties(new File("src/test/resources/mailer-test.properties"));
        Mailer mailer = Mailer.mailer(props).make();
        Assert.assertEquals(mailer.getHost(), "smtp.mailer-test.com");
        Assert.assertEquals(mailer.getUser(), "test");
        Assert.assertEquals(mailer.getPassword(), "test123");
    }

    //@Test
    public void simplest() throws IOException {

        IO.mkDirs(IO.USER_DIR + "/config");
        IO.writeFile(IO.USER_DIR + "/config/mailer.properties",
                "mail.host=smtp.gmail.com" + IO.NEW_LINE +
                        "mail.user=womenofworthinessconf@gmail.com" + IO.NEW_LINE +
                        "mail.password=********" + IO.NEW_LINE +
                        "mail.smtp.starttls.enable=true" + IO.NEW_LINE +
                        "mail.smtp.auth=true");

        Mailer.mail().to("cworkscode@gmail.com")
            .subject("Hi")
            .body("Welcome")
            .send();

        IO.rmDirs(IO.USER_DIR + "/config");
    }

    //@Test
    public void simplestWithCallack() throws IOException {

        IO.mkDirs(IO.USER_DIR + "/config");
        IO.writeFile(IO.USER_DIR + "/config/mailer.properties",
                "mail.host=smtp.gmail.com" + IO.NEW_LINE +
                        "mail.user=womenofworthinessconf@gmail.com" + IO.NEW_LINE +
                        "mail.password=********" + IO.NEW_LINE +
                        "mail.smtp.starttls.enable=true" + IO.NEW_LINE +
                        "mail.smtp.auth=true");

        Mailer.mail().to("cworkscode@gmail.com")
                .subject("Hi")
                .body("Welcome")
                .send(new MailerCallback() {
                    public void onSent(MailerEvent event) {
                        System.out.println("onSent:" + event.getMessage());
                    }
                    public void onError(MailerEvent event) {
                        System.out.println("onError:" + event.getMessage());
                    }
                    public void onStatus(MailerEvent event) {
                        System.out.println("onStatus:" + event.getMessage());
                    }
                });

        IO.rmDirs(IO.USER_DIR + "/config");
    }

    //@Test
    public void example() {

        Mail mail = Mailer.mailer()
            .host("smtp.gmail.com")
            .user("womenofworthinessconf@gmail.com")
            .password("********")
            .property("mail.smtp.starttls.enable", "true")
            .property("mail.smtp.auth", "true")
            .mail().to("cworkscode@gmail.com")
            .subject("Welcome")
            .body("Welcome aboard mate!")
            .attachment(new File("src/test/resources/nacho_libre_poster.jpg"))
            .make();

        mail.send();

    }

    //@Test
    public void htmlExample() {

        Mail mail = Mailer.mailer()
                .host("smtp.gmail.com")
                .user("womenofworthinessconf@gmail.com")
                .password("********")
                .property("mail.smtp.starttls.enable", "true")
                .property("mail.smtp.auth", "true")
                .mail().to("cworkscode@gmail.com")
                .subject("Welcome to the world of HTML emails")
                .body("<html><h1>Welcome aboard mate!</h1></html>")
                .attachment(new File("src/test/resources/nacho_libre_poster.jpg"))
                .make();

        mail.send();

    }

    //@Test
    public void mailerWithClass() {

        Mail customMail = new CustomMail();
        Mail mail = Mailer.mailer()
            .host("smtp.gmail.com")
            .user("cworkscode@gmail.com")
            .password("********")
            .mail(customMail)
            .make();

        mail.send();
    }
}
