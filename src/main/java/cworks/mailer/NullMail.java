package cworks.mailer;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NullMail implements Mail {

    public NullMail() {

    }

    @Override
    public List<String> getToList() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getCcList() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getBccList() {
        return new ArrayList<>();
    }

    @Override
    public List<File> getAttachments() {
        return new ArrayList<>();
    }

    @Override
    public String getFrom() {
        return "";
    }

    @Override
    public String getSubject() {
        return "";
    }

    @Override
    public String getBody() {
        return "";
    }

    @Override
    public void send() {

    }
}
