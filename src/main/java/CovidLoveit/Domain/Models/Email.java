package CovidLoveit.Domain.Models;

import java.util.Date;

public class Email {
    private String emailFrom;

    private String emailTo;

    private String emailSubject;

    private String emailContent;

    private String contentType;

    public Email(String emailFrom, String emailTo, String emailSubject, String emailContent, String contentType) {
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.emailSubject = emailSubject;
        this.emailContent = emailContent;
        this.contentType = contentType;
    }

    public Email(String emailTo, String emailSubject, String emailContent) {
        this.emailTo = emailTo;
        this.emailSubject = emailSubject;
        this.emailContent = emailContent;
    }

    public Email() {
        contentType = "text/plain";
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    private Date getEmailSendDate() {
        return new Date();
    }
}
