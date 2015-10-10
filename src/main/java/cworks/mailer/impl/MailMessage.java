package cworks.mailer.impl;

import cworks.mailer.Mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class MailMessage implements Mail {

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

        try {
            Session session = Session.getDefaultInstance(new Properties());
            Message message = new MimeMessage(session);

            InternetAddress[] addresses = new InternetAddress[getToList().size()];

            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setFrom(new InternetAddress(getFrom()));

            addresses = new InternetAddress[getCcList().size()];
            message.setRecipients(Message.RecipientType.CC, addresses);

            addresses = new InternetAddress[getBccList().size()];
            message.setRecipients(Message.RecipientType.BCC, addresses);

            message.setSubject(getSubject());
            message.setText(getBody());

            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }


}
