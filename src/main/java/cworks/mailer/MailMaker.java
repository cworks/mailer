package cworks.mailer;

import java.io.File;
import java.util.ArrayList;
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
    private Mail mailInstance;
    private Mailer mailer;

    public MailMaker() {
        this.mailInstance = null;
        init();
    }

    public MailMaker(Mail mailInstance) {
        this.mailInstance = mailInstance;
        init();
    }

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

    public void mailer(Mailer mailer) {
        this.mailer = mailer;
    }

    /**
     * MailMaker make that Mail, make that Mail, make that Mail.
     * @return
     */
    public Mail make() {

        MailerMail mail = null;
        if(mailInstance != null) {
            mail = new MailerMail(mailInstance);
        } else {
            mail = new MailerMail();
        }

        if(mailer.getUser() != null) {
            mail.setFrom(mailer.getUser());
        }
        if(this.from != null) {
            mail.setFrom(this.from);
        }

        if(this.toList.size() == 0 || mail.getFrom() == null) {
            return new NullMail();
        }

        mail.setToList(toList);
        mail.setCcList(ccList);
        mail.setBccList(bccList);
        mail.setSubject(subject);
        mail.setBody(body);
        mail.setAttachments(attachments);
        mail.setMailer(mailer);

        return mail;
    }

    public void send() {
        Mail mail = make();
        this.mailer.send(mail);
    }

    public void send(MailerCallback callback) {
        Mail mail = make();
        this.mailer.send(mail, callback);
    }

    private void init() {
        this.toList = new ArrayList<>();
        this.ccList = new ArrayList<>();
        this.bccList = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

}
