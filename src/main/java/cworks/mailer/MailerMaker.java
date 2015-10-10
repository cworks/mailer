package cworks.mailer;

import java.util.Properties;

public class MailerMaker {

    private String server;
    private String username;
    private String password;
    private String protocol;
    private int port;

    public MailerMaker server(String server) {
        this.server = server;
        return this;
    }

    public MailerMaker port(int port) {
        this.port = port;
        return this;
    }

    public MailerMaker protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public MailerMaker username(String username) {
        this.username = username;
        return this;
    }

    public MailerMaker password(String password) {
        this.password = password;
        return this;
    }

    public MailMaker mail(String from) {
        MailMaker mailMaker = new MailMaker();
        mailMaker.from(from);
        return mailMaker;
    }

    public Mailer make() {

        Mailer mailer = new Mailer();
        mailer.setServer(server);
        mailer.setUsername(username);
        mailer.setPassword(password);

        return mailer;
    }

    public MailerMaker properties(Properties properties) {
        server = properties.getProperty("mailer.server");
        username = properties.getProperty("mailer.username");
        password = properties.getProperty("mailer.password");
        return this;
    }
}
