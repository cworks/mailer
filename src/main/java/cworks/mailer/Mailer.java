package cworks.mailer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

public class Mailer {

    public static MailerMaker create(String server) {
        return new MailerMaker();
    }

    public static Mailer create() {
        try {
            Properties properties = getClassPathProperties();
            System.out.println(properties);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new MailerMaker().make();
    }

    public static MailMaker mail(String from) {
        return new MailMaker();
    }

    /**
     * Create a Mailer from configuration obtained based on these precedence rules
     *
     * mailer.properties inside jar
     * mailer.properties outside jar in current dir or config dir
     * os env
     * java system properties
     * jndi properties, java:comp/env
     * command line args
     *
     * @return
     */
    private static Properties getClassPathProperties() throws IOException {

        Properties properties = getClassPathProperties("mailer.properties");
        if(properties != null) {
            return properties;
        }

        properties = getClassPathProperties("config/mailer.properties");
        if(properties != null) {
            return properties;
        }

        properties = getClassPathProperties("properties/mailer.properties");
        if(properties != null) {
            return properties;
        }

        properties = getClassPathProperties("mailer/mailer.properties");
        if(properties != null) {
            return properties;
        }

        properties = getClassPathProperties("mailer/config/mailer.properties");
        if(properties != null) {
            return properties;
        }

        properties = getClassPathProperties("mailer/properties/mailer.properties");
        if(properties != null) {
            return properties;
        }

        properties  = getClassPathProperties("resources/mailer.properties");
        if(properties != null) {
            return properties;
        }

        properties = getClassPathProperties("resources/config/mailer.properties");
        if(properties != null) {
            return properties;
        }

        properties = getClassPathProperties("resources/properties/mailer.properties");
        if(properties != null) {
            return properties;
        }

        return null;
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

    private static void printClasspath() throws IOException {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
        for(URL url: urls){
            System.out.println(url.getFile());
        }
    }
}
