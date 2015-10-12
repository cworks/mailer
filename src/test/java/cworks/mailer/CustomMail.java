package cworks.mailer;

import java.io.File;
import java.util.List;

public class CustomMail implements Mail {
    @Override
    public List<String> getToList() {
        return null;
    }

    @Override
    public List<String> getCcList() {
        return null;
    }

    @Override
    public List<String> getBccList() {
        return null;
    }

    @Override
    public List<File> getAttachments() {
        return null;
    }

    @Override
    public String getFrom() {
        return null;
    }

    @Override
    public String getSubject() {
        return null;
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public void send() {

    }
}
