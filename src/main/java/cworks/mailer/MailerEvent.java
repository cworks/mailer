package cworks.mailer;

public class MailerEvent {

    private String message;

    public MailerEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return getMessage();
    }
}
