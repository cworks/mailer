package cworks.mailer;

public class MailerMaker {

    public MailerMaker server(String server) {
        return this;
    }

    public MailerMaker port(int port) {
        return this;
    }

    public MailerMaker protocol(String protocol) {
        return this;
    }

    public MailerMaker username(String username) {
        return this;
    }

    public MailerMaker password(String password) {
        return this;
    }

    public MailMaker mail(String from) {
        return new MailMaker();
    }

    public Mailer make() {
        return new Mailer();
    }
}
