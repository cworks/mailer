package cworks.mailer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Mailer {

    private static final List<String> PROP_LIST = Arrays.asList(
        IO.USER_DIR + "/mailer.properties",
        IO.USER_DIR + "/config/mailer.properties",
        IO.USER_DIR + "/properties/mailer.properties",
        IO.USER_DIR + "/mailer/mailer.properties",
        IO.USER_DIR + "/mailer/config/mailer.properties",
        IO.USER_DIR + "/mailer/properties/mailer.properties",
        IO.USER_DIR + "/mailer/resources/mailer.properties",
        IO.USER_DIR + "/mailer/resources/config/mailer.properties",
        IO.USER_DIR + "/mailer/resources/properties/mailer.properties");

    private Properties properties;
    private String host;
    private int port;
    private String user;
    private String password;
    private String protocol;

    public static MailerMaker mailer(String host) {
        MailerMaker maker = new MailerMaker();
        maker.host(host);
        return maker;
    }

    public static MailerMaker mailer(final Properties properties) {
        MailerMaker maker = new MailerMaker();

        try {
            Properties merged = new Properties();
            Properties internal = getCombinedProperties();
            merged.putAll(internal);
            merged.putAll(properties);
            String server = merged.getProperty("mail.host");
            String username = merged.getProperty("mail.user");
            String password = merged.getProperty("mail.password");

            maker.host(server);
            maker.user(username);
            maker.password(password);
        } catch(IOException ex) {
            // eat
        }

        return maker;
    }

    /**
     * Create default mailer:
     * Inside jar/classpath
     * Outside jar/file-system
     * Java System properties
     *
     * @return the Mailer
     */
    public static MailerMaker mailer() {

        MailerMaker maker = new MailerMaker();
        try {
            Properties properties = getCombinedProperties();
            String server = properties.getProperty("mail.host");
            String user = properties.getProperty("mail.user");
            String pass = properties.getProperty("mail.password");
            maker.host(server);
            maker.user(user);
            maker.password(pass);
            maker.properties(properties);
        } catch (IOException ex) {
            // eat
        }

        return maker;
    }

    /**
     * Basic mailer creation method
     * @return Mailer
     */
    public static Mailer make() {
        return mailer().make();
    }

    public static MailMaker mail() {
        return mailer().mail();
    }

    public static MailMaker mail(String from) {
        return mailer().mail().from(from);
    }

    private static Properties getFileSysProperties() throws IOException {

        for(String propFile : PROP_LIST) {
            try {
                Properties properties = IO.asProperties(new File(propFile));
                if (properties != null) {
                    return properties;
                }
            } catch(IOException ex) {
                // eat
            }
        }

        return new Properties();
    }

    private static Properties getClassPathProperties() throws IOException {

        for(String propFile : PROP_LIST) {
            Properties properties = getClassPathProperties(propFile);
            if(properties != null) {
                return properties;
            }
        }

        return new Properties();
    }

    private static Properties getSysProperties() {

        Properties properties = new Properties();
        String value = System.getProperty("mail.host");
        if(value != null) {
            properties.put("mail.host", value);
        }
        value = System.getProperty("mail.user");
        if(value != null) {
            properties.put("mail.user", value);
        }
        value = System.getProperty("mail.password");
        if(value != null) {
            properties.put("mail.password", value);
        }
        value = System.getProperty("mail.port");
        if(value != null) {
            properties.put("mail.port", value);
        }
        value = System.getProperty("mail.transport.protocol");
        if(value != null) {
            properties.put("mail.transport.protocol", value);
        }

        return properties;
    }

    private static Properties getCombinedProperties() throws IOException {
        Properties cpProps = getClassPathProperties();
        Properties fsProps = getFileSysProperties();
        Properties clProps = getSysProperties();

        Properties properties = new Properties();
        properties.putAll(cpProps);
        properties.putAll(fsProps);
        properties.putAll(clProps);
        return properties;
    }

    private static Properties getClassPathProperties(String path) throws IOException {

        Properties properties = null;
        InputStream in = null;

        try {
            in = Mailer.class.getClassLoader().getResourceAsStream(path);
            if(in == null) {
                return null;
            }
            properties = new Properties();
            properties.load(in);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException ex) { }
            }
        }

        return properties;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return properties;
    }

    void setHost(String host) {
        this.host = host;
    }

    void setPort(int port) {
        this.port = port;
    }

    void setUser(String user) {
        this.user = user;
    }

    void setPassword(String password) {
        this.password = password;
    }

    void setProperties(Properties properties) {
        this.properties = properties;
    }

    void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void send(final Mail mail) throws MailerException {
        send(mail, null);
    }

    public void send(final Mail mail, final MailerCallback callback) {

        try {
            Session session = Session.getDefaultInstance(getProperties());

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail.getFrom()));
            message.setSubject(mail.getSubject());

            List<String> toList = mail.getToList();
            if (toList != null) {
                InternetAddress[] to = new InternetAddress[toList.size()];
                for (int i = 0; i < to.length; i++) {
                    to[i] = new InternetAddress(toList.get(i));
                }
                message.setRecipients(Message.RecipientType.TO, to);
            }

            List<String> ccList = mail.getCcList();
            if (ccList != null) {
                InternetAddress[] cc = new InternetAddress[ccList.size()];
                for (int i = 0; i < cc.length; i++) {
                    cc[i] = new InternetAddress(ccList.get(i));
                }
                message.setRecipients(Message.RecipientType.CC, cc);
            }

            List<String> bccList = mail.getBccList();
            if (bccList != null) {
                InternetAddress[] bcc = new InternetAddress[bccList.size()];
                for (int i = 0; i < bcc.length; i++) {
                    bcc[i] = new InternetAddress(bccList.get(i));
                }
                message.setRecipients(Message.RecipientType.BCC, bcc);
            }

            // create a multipart email message
            Multipart multipart = new MimeMultipart();

            // create message body part
            MimeBodyPart bodyPart = new MimeBodyPart();
            // create text or html body
            createBody(mail, bodyPart);
            // Collect the Parts into the MultiPart
            multipart.addBodyPart(bodyPart);

            // attach files if necessary
            List<MimeBodyPart> attachList = attachFiles(mail);
            for(MimeBodyPart part : attachList) {
                multipart.addBodyPart(part);
            }

            // Put the MultiPart into the Message
            message.setContent(multipart);
            // save and send it...
            message.saveChanges();

            send(session, message, callback);

        } catch(MessagingException ex) {
            throw new MailerException(ex);
        }
    }

    private void createBody(Mail mail, MimeBodyPart bodyPart) throws MessagingException {

        String body = mail.getBody().trim();
        if(body.startsWith("<html>") && body.endsWith("</html>")) {
            bodyPart.setContent(body, "text/html");
        } else {
            bodyPart.setContent(body, "text/plain");
        }
    }

    private List<MimeBodyPart> attachFiles(Mail mail) {
        List<MimeBodyPart> parts = new ArrayList<>();
        if(mail.getAttachments() != null && mail.getAttachments().size() > 0) {
            List<File> attachments = mail.getAttachments();
            for(File attachment : attachments) {
                try {
                    MimeBodyPart bodyPart = new MimeBodyPart();
                    bodyPart.attachFile(attachment);
                    parts.add(bodyPart);
                } catch (IOException | MessagingException ex) {
                    // log something but don't bail on the email
                }
            }
        }

        return parts;
    }

    private void send(final Session session, final Message message, final MailerCallback callback) {

        Transport transport = null;
        MailerConnectionListener cl = null;
        MailerTransportListener tl = null;
        try {
            transport = session.getTransport(getProtocol());

            if(callback != null) {
                cl = new MailerConnectionListener(callback);
                tl = new MailerTransportListener(callback);
                transport.addConnectionListener(cl);
                transport.addTransportListener(tl);
            }
            transport.connect(getHost(),
                    getPort(),
                    getUser(),
                    getPassword());

            transport.sendMessage(message, message.getAllRecipients());
        } catch(MessagingException ex) {
            throw new MailerException(ex);
        } finally {
            closeQuietly(transport, cl, tl);
        }
    }

    private static void closeQuietly(Transport transport,
        MailerConnectionListener cl, MailerTransportListener tl) {

        try {
            if(transport != null) {
                transport.close();

                if(tl != null) {
                    transport.removeTransportListener(tl);
                }
                if(cl != null) {
                    transport.removeConnectionListener(cl);
                }
            }
        } catch (final MessagingException ignore) { }
    }

    private static class MailerConnectionListener implements ConnectionListener {

        private MailerCallback callback;

        public MailerConnectionListener(MailerCallback callback) {
            this.callback = callback;
        }

        @Override
        public void opened(ConnectionEvent event) {
            MailerEvent mailerEvent = new MailerEvent("opened: " + event.toString());
            callback.onStatus(mailerEvent);
        }

        @Override
        public void disconnected(ConnectionEvent event) {
            MailerEvent mailerEvent = new MailerEvent("disconnected: " + event.toString());
            callback.onStatus(mailerEvent);
        }

        @Override
        public void closed(ConnectionEvent event) {
            MailerEvent mailerEvent = new MailerEvent("closed: " + event.toString());
            callback.onStatus(mailerEvent);
        }
    }

    private static class MailerTransportListener implements TransportListener {

        private MailerCallback callback;

        public MailerTransportListener(MailerCallback callback) {
            this.callback = callback;
        }

        @Override
        public void messageDelivered(TransportEvent event) {
            MailerEvent mailerEvent = new MailerEvent("messageDelivered: " + event.toString());
            callback.onSent(mailerEvent);
        }

        @Override
        public void messageNotDelivered(TransportEvent event) {
            MailerEvent mailerEvent = new MailerEvent("messageNotDelivered: " + event.toString());
            callback.onError(mailerEvent);
        }

        @Override
        public void messagePartiallyDelivered(TransportEvent event) {
            MailerEvent mailerEvent = new MailerEvent("messagePartiallyDelivered: "
                    + event.toString());
            callback.onError(mailerEvent);
        }
    }

}
