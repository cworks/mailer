package cworks.mailer;

import java.util.Properties;

public class MailerMaker {

    private String host;
    private String user;
    private String password;
    private String protocol;
    private Integer port;
    private Properties properties;

    public MailerMaker properties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public MailerMaker property(String name, String value) {
        if(this.properties == null) {
            this.properties = new Properties();
        }
        this.properties.setProperty(name, value);
        return this;
    }

    public MailerMaker host(String host) {
        this.host = host;
        return this;
    }

    public MailerMaker port(int port) {
        this.port = port;
        return this;
    }

    public MailerMaker user(String user) {
        this.user = user;
        return this;
    }

    public MailerMaker password(String password) {
        this.password = password;
        return this;
    }

    public MailerMaker protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public MailMaker mail(Mail mailInstance) {
        MailMaker maker;
        if(mailInstance != null) {
            // wrap mailInstance with the maker with can override mailInstance properties
            maker = new MailMaker(mailInstance);
        } else {
            // plain-ole default MailerMail will be used
            maker = new MailMaker();
        }

        maker.mailer(make());
        return maker;
    }

    /**
     * Start the Mail making process
     * @return MailMaker
     */
    public MailMaker mail() {
        MailMaker maker = new MailMaker();
        maker.mailer(make());
        return maker;
    }

    /**
     * MailerMaker make that Mailer
     * @return Mailer
     */
    public Mailer make() {

        Mailer mailer = new Mailer();

        if(properties == null) {
            properties = new Properties();
        }

        if(host == null) {
            host = properties.getProperty("mail.host");
        } else {
            properties.setProperty("mail.host", host);
        }

        if(user == null) {
            user = properties.getProperty("mail.user");
        } else {
            properties.setProperty("mail.user", user);
        }

        if(password == null) {
            password = properties.getProperty("mail.password");
        } else {
            properties.setProperty("mail.password", password);
        }

        if(port == null) {
            port = Integer.valueOf(properties.getProperty("mail.port", "25"));
        } else {
            properties.setProperty("mail.port", String.valueOf(port));
        }

        if(protocol == null) {
            protocol = properties.getProperty("mail.transport.protocol", "smtp");
        } else {
            properties.setProperty("mail.transport.protocol", protocol);
        }

        mailer.setHost(host);
        mailer.setUser(user);
        mailer.setPassword(password);
        mailer.setPort(port);
        mailer.setProtocol(protocol);
        mailer.setProperties(properties);

        return mailer;
    }



}
