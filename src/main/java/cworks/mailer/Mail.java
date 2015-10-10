package cworks.mailer;

import java.util.List;

public interface Mail {

    List<String> getToList();
    List<String> getCcList();
    List<String> getBccList();
    String getFrom();
    String getSubject();
    String getBody();
    void send();
}
