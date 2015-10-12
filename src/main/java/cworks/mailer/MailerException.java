package cworks.mailer;

import javax.mail.MessagingException;

public class MailerException extends RuntimeException {
    public MailerException(MessagingException ex) {

    }
}
