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
                "mailer.server=smtp.config.com" + IO.NEW_LINE +
                        "mailer.username=corbett" + IO.NEW_LINE +
                        "mailer.password=wordup");
        Mailer mailer = Mailer.make();
        Assert.assertEquals(mailer.getServer(), "smtp.config.com");
        Assert.assertEquals(mailer.getUsername(), "corbett");
        Assert.assertEquals(mailer.getPassword(), "wordup");
        IO.rmDirs(IO.USER_DIR + "/config");

        IO.mkDirs(IO.USER_DIR + "/mailer/properties");
        IO.writeFile(IO.USER_DIR + "/mailer/properties/mailer.properties",
                "mailer.server=smtp.mailer.properties.com" + IO.NEW_LINE +
                        "mailer.username=corbett" + IO.NEW_LINE +
                        "mailer.password=toejam");
        mailer = Mailer.make();
        Assert.assertEquals(mailer.getServer(), "smtp.mailer.properties.com");
        Assert.assertEquals(mailer.getUsername(), "corbett");
        Assert.assertEquals(mailer.getPassword(), "toejam");
        IO.rmDirs(IO.USER_DIR + "/mailer");
    }

    @Test
    public void mailerCustomConfig() throws IOException {
        Properties props = IO.asProperties(new File("src/test/resources/mailer-test.properties"));
        Mailer mailer = Mailer.mailer(props).make();
        Assert.assertEquals(mailer.getServer(), "smtp.mailer-test.com");
        Assert.assertEquals(mailer.getUsername(), "test");
        Assert.assertEquals(mailer.getPassword(), "test123");
    }

    @Test
    public void example() {

        Mail mail = Mailer.mailer()
            .server("smtp.gmail.com")
            .username("cworkscode@gmail.com")
            .password("javaISCOOL123")
            .mail("cworkscode@gmail.com")
            .to("cworkscode@gmail.com")
            .subject("Welcome")
            .body("Welcome aboard mate!")
            .attachment(new File("src/test/resources/nacho_libre_poster.jpg"))
            .make();

        mail.send();

    }
}
