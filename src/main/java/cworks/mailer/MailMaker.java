package cworks.mailer;

import cworks.mailer.impl.MailMessage;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class MailMaker {
    private String from;
    private String subject;
    private List<String> toList;
    private List<String> ccList;
    private List<String> bccList;
    private List<File> attachments;
    private String body;

    public MailMaker from(String from) {
        this.from = from;
        return this;
    }

    public MailMaker subject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailMaker to(String to) {
        this.toList.add(to);
        return this;
    }

    public MailMaker to(String... toList) {
        Collections.addAll(this.toList, toList);
        return this;
    }

    public MailMaker to(List<String> toList) {
        this.toList.addAll(toList);
        return this;
    }

    public MailMaker cc(String cc) {
        this.ccList.add(cc);
        return this;
    }

    public MailMaker cc(String... ccList) {
        Collections.addAll(this.ccList, ccList);
        return this;
    }

    public MailMaker cc(List<String> ccList) {
        this.ccList.addAll(ccList);
        return this;
    }

    public MailMaker bcc(String bcc) {
        this.bccList.add(bcc);
        return this;
    }

    public MailMaker bcc(String... bccList) {
        Collections.addAll(this.bccList, bccList);
        return this;
    }

    public MailMaker bcc(List<String> bccList) {
        this.bccList.addAll(bccList);
        return this;
    }

    public MailMaker attachment(File attachment) {
        this.attachments.add(attachment);
        return this;
    }

    public MailMaker attachment(File... attachment) {
        Collections.addAll(this.attachments, attachment);
        return this;
    }

    public MailMaker attachment(List<File> attachment) {
        this.attachments.addAll(attachment);
        return this;
    }

    public MailMaker body(String message) {
        this.body = message;
        return this;
    }

    public Mail make() {

        Mail mail = new MailMessage();

        return null;
    }

    public void send() {

    }

    public void send(Mail mail) {

    }

    public void send(final SendListener listener) {

    }

}
