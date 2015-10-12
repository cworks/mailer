package cworks.mailer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MailerMail implements Mail {

    private String from;
    private String subject;
    private List<String> toList;
    private List<String> ccList;
    private List<String> bccList;
    private List<File> attachments;
    private String body;
    private Mail mailInstance;
    private Mailer mailer;

    public MailerMail() {
        this.mailInstance = null;
    }

    public MailerMail(Mail mailInstance) {
        this.mailInstance = mailInstance;
    }

    @Override
    public List<String> getToList() {
        return new ArrayList<>(toList);
    }

    @Override
    public List<String> getCcList() {
        return new ArrayList<>(ccList);
    }

    @Override
    public List<String> getBccList() {
        return new ArrayList<>(bccList);
    }

    @Override
    public List<File> getAttachments() {
        return new ArrayList<>(attachments);
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getBody() {
        return body;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
    }

    public void setCcList(List<String> ccList) {
        this.ccList = ccList;
    }

    public void setBccList(List<String> bccList) {
        this.bccList = bccList;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setMailer(Mailer mailer) {
        this.mailer = mailer;
    }

    @Override
    public void send() {
        mailer.send(this);
    }
}
