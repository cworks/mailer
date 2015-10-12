package cworks.mailer;

public interface MailerCallback {
    void onSent(MailerEvent event);
    void onError(MailerEvent event);
    void onStatus(MailerEvent event);
}
