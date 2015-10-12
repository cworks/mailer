package cworks.mailer;

import java.io.File;
import java.util.List;

public interface Mail {
    List<String> getToList();
    List<String> getCcList();
    List<String> getBccList();
    List<File> getAttachments();
    String getFrom();
    String getSubject();
    String getBody();
    void send();
}
