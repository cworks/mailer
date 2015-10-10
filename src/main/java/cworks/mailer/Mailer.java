package cworks.mailer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private String server;
    private String username;
    private String password;

    public static MailerMaker mailer(String server) {
        return new MailerMaker();
    }

    public static MailerMaker mailer(final Properties properties) {
        MailerMaker maker = new MailerMaker();

        try {
            Properties merged = new Properties();
            Properties internal = getProperties();
            merged.putAll(internal);
            merged.putAll(properties);
            String server = merged.getProperty("mailer.server");
            String username = merged.getProperty("mailer.username");
            String password = merged.getProperty("mailer.password");

            maker.server(server);
            maker.username(username);
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
            Properties properties = getProperties();
            String server = properties.getProperty("mailer.server");
            String username = properties.getProperty("mailer.username");
            String password = properties.getProperty("mailer.password");

            maker.server(server);
            maker.username(username);
            maker.password(password);
        } catch (IOException ex) {
            // eat
        }

        return maker;
    }

    public static Mailer make() {
        Mailer mailer = null;
        try {
            Properties properties = getProperties();
            String server = properties.getProperty("mailer.server");
            String username = properties.getProperty("mailer.username");
            String password = properties.getProperty("mailer.password");

            mailer = new Mailer();
            mailer.setServer(server);
            mailer.setUsername(username);
            mailer.setPassword(password);
        } catch (IOException ex) {
            // eat
        }

        return mailer;
    }

    public static MailMaker mail(String from) {
        return new MailMaker();
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
        String value = System.getProperty("mailer.server");
        if(value != null) {
            properties.put("mailer.server", value);
        }
        value = System.getProperty("mailer.username");
        if(value != null) {
            properties.put("mailer.username", value);
        }
        value = System.getProperty("mailer.password");
        if(value != null) {
            properties.put("mailer.password", value);
        }

        return properties;
    }

    private static Properties getProperties() throws IOException {
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

    public String getServer() {
        return server;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    void setServer(String server) {
        this.server = server;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void setPassword(String password) {
        this.password = password;
    }

    void setProperties(Properties properties) {
        this.properties = properties;
    }


}
