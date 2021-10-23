package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Email;
import CovidLoveit.Domain.Models.Guideline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class EmailServiceImpl {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(email.getEmailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(email.getEmailFrom(), "TRAIL"));
            mimeMessageHelper.setTo(email.getEmailTo());
            mimeMessageHelper.setText(email.getEmailContent(), true);


            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void newClientCreationEmail(String email) {
        Email emailToSend = new Email();

        emailToSend.setEmailSubject("Client Account Creation");
        emailToSend.setEmailFrom("TRAIL");

        String message = "Dear " + email + "," + "\n\n Your Account has been successfully created.";
        emailToSend.setEmailContent(message);
        emailToSend.setEmailTo(email);
        sendEmail(emailToSend);
    }

    public void newGuidelineCreationEmail(String email, GuidelineInputModel guidelineInputModel) {
        Email emailToSend = new Email();

        emailToSend.setEmailSubject("New Guideline Announcement");
        emailToSend.setEmailFrom("TRAIL");

        StringBuilder message = new StringBuilder("Dear " + email + "," +
                "\n\n The newly added guideline for your establishment: ");

        message.append("\nOperation Guidelines: " + guidelineInputModel.getOpGuidelines() + "\n");
        message.append("\nCan Operate on site?: " + guidelineInputModel.getCanOpOnSiteDetails());
        message.append("\nOperation on site details: " + guidelineInputModel.getCanOpOnSiteDetails());
        message.append("\nContact tracing requirement: " + guidelineInputModel.getContactTracing());
        message.append("\nContact tracing details: " + guidelineInputModel.getContactTracingDetails());
        message.append("\nAllowed group size: " + guidelineInputModel.getGroupSize());
        message.append("\nGroup size details: " + guidelineInputModel.getGroupSizeDetails());
        message.append("\nOperating capacity: " + guidelineInputModel.getOpCapacity());
        message.append("\nOperating capacity details: " + guidelineInputModel.getOpCapacityDetails());
        message.append("\nCovid testing details: " + guidelineInputModel.getCovidTestingDetails());
        message.append("\nVaccinated: " + guidelineInputModel.getCovidTestingVaccinated());
        message.append("\nUnvaccinated: " + guidelineInputModel.getCovidTestingUnvaccinated());

        String formedMessage = message.toString();
        emailToSend.setEmailContent(formedMessage);
        emailToSend.setEmailTo(email);
        sendEmail(emailToSend);
    }

    public void updateGuidelineNotificationEmail(String email, GuidelineInputModel guidelineInputModel) {
        Email emailToSend = new Email();

        emailToSend.setEmailSubject("Updated Guideline Notification");
        emailToSend.setEmailFrom("TRAIL");

        StringBuilder message = new StringBuilder("Dear " + email + "," +
                "\n\n There is an updated guideline for your establishment: ");

        message.append("\nOperation Guidelines: " + guidelineInputModel.getOpGuidelines() + "\n");
        message.append("\nCan Operate on site?: " + guidelineInputModel.getCanOpOnSiteDetails());
        message.append("\nOperation on site details: " + guidelineInputModel.getCanOpOnSiteDetails());
        message.append("\nContact tracing requirement: " + guidelineInputModel.getContactTracing());
        message.append("\nContact tracing details: " + guidelineInputModel.getContactTracingDetails());
        message.append("\nAllowed group size: " + guidelineInputModel.getGroupSize());
        message.append("\nGroup size details: " + guidelineInputModel.getGroupSizeDetails());
        message.append("\nOperating capacity: " + guidelineInputModel.getOpCapacity());
        message.append("\nOperating capacity details: " + guidelineInputModel.getOpCapacityDetails());
        message.append("\nCovid testing details: " + guidelineInputModel.getCovidTestingDetails());
        message.append("\nVaccinated: " + guidelineInputModel.getCovidTestingVaccinated());
        message.append("\nUnvaccinated: " + guidelineInputModel.getCovidTestingUnvaccinated());

        String formedMessage = message.toString();
        emailToSend.setEmailContent(formedMessage);
        emailToSend.setEmailTo(email);
        sendEmail(emailToSend);
    }


}
