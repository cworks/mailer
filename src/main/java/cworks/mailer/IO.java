package cworks.mailer;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public final class IO {

    public static final String USER_DIR = System.getProperty("user.dir");

    public static final String SLASH = System.getProperty("file.separator");

    public static final String NEW_LINE = System.getProperty("line.separator");

    public static String asPathString(String ... parts) {
        return String.join(SLASH, parts);
    }

    public static Properties asProperties(final File file) throws IOException {
        Reader reader = new FileReader(file);
        Properties properties = new Properties();
        properties.load(reader);
        closeQuietly(reader);
        return properties;
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if(closeable != null) { closeable.close(); }
        } catch (final IOException ignore) { }
    }

    public static void mkDirs(String ... parts) throws IOException {
        Files.createDirectories(Paths.get(asPathString(parts)));
    }

    public static void rmDirs(String ... parts) throws IOException {
        String path = asPathString(parts);
        File dir = new File(path);
        if(dir.isDirectory()) {
            removeDirs(parts);
        }
    }

    private static void removeDirs(String ... parts) throws IOException {

        String path = asPathString(parts);
        File dir = new File(path);

        if(dir.isDirectory()) {
            if(dir.list().length == 0) {
                dir.delete();
            } else {
                String files[] = dir.list();
                for (String temp : files) {
                    File fileDelete = new File(dir, temp);
                    removeDirs(fileDelete.getPath());
                }

                if(dir.list().length == 0) {
                    dir.delete();
                }
            }
        } else {
            dir.delete();
        }
    }

    public static void writeFile(String path, String content) throws IOException {
        Writer writer = asWriter(path);
        writer.write(content);
        closeQuietly(writer);
    }

    public static Writer asWriter(final File file) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(file));
        return writer;
    }

    public static Writer asWriter(String path) throws IOException {
        return asWriter(new File(path));
    }


}
