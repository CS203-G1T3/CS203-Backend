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

        String message = "Dear " + email + "," + "<p>" + "Your Account has been successfully created." + "</p>";
        emailToSend.setEmailContent(message);
        emailToSend.setEmailTo(email);
        sendEmail(emailToSend);
    }

    public void newGuidelineCreationEmail(String email, GuidelineInputModel guidelineInputModel) {
        Email emailToSend = new Email();

        emailToSend.setEmailSubject("New Guideline Announcement");
        emailToSend.setEmailFrom("TRAIL");

        StringBuilder message = new StringBuilder("Dear " + email + "," +
                "<p>" + "The newly added guideline for your establishment: " + "</p>");

        message.append("<p>" + "<b>" + "Operation Guidelines: " + "</b>" + guidelineInputModel.getOpGuidelines()  + "</p>");
        message.append("<p>" + "<b>" + "Can Operate on site?: "  + "</b>" + "<span style='color:#FF0000'>" + guidelineInputModel.isCanOpOnSite() + "</span" + "</p>");
        message.append("<p>" + "<b>" + "Operation on site details: "  + "</b>"  + guidelineInputModel.getCanOpOnSiteDetails() + "</p>");
        message.append("<p>" + "<b>" + "Contact tracing requirement: "  + "</b>"  + guidelineInputModel.getContactTracing() + "</p>");
        message.append("<p>" + "<b>" + "Contact tracing details: "  + "</b>"  + guidelineInputModel.getContactTracingDetails() + "</p>");
        message.append("<p>" + "<b>" + "Allowed group size: "  + "</b>"  + guidelineInputModel.getGroupSize() + "</p>");
        message.append("<p>" + "<b>" + "Group size details: "  + "</b>"  + guidelineInputModel.getGroupSizeDetails() + "</p>");
        message.append("<p>" + "<b>" + "Operating capacity: "  + "</b>"  + "<span style='color:#FF0000'>" + guidelineInputModel.getOpCapacity() + "</span>" + "</p>");
        message.append("<p>" + "<b>" + "Operating capacity details: "  + "</b>"  + guidelineInputModel.getOpCapacityDetails() + "</p>");
        message.append("<p>" + "<b>" + "Covid testing details: "  + "</b>"  + guidelineInputModel.getCovidTestingDetails() + "</p>");
        message.append("<p>" + "<b>" + "Vaccinated: "  + "</b>"  + guidelineInputModel.getCovidTestingVaccinated() + "</p>");
        message.append("<p>" + "<b>" + "Unvaccinated: "  + "</b>"  + guidelineInputModel.getCovidTestingUnvaccinated() + "</p>");

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
                "<p>" + "Updated guideline for your establishment: " + "</p>");

        message.append("<p>" + "<b>" + "Operation Guidelines: " + "</b>" + guidelineInputModel.getOpGuidelines()  + "</p>");
        message.append("<p>" + "<b>" + "Can Operate on site?: "  + "</b>" + "<span style='color:#FF0000'>" + guidelineInputModel.isCanOpOnSite() + "</span>" + "</p>");
        message.append("<p>" + "<b>" + "Operation on site details: "  + "</b>"  + guidelineInputModel.getCanOpOnSiteDetails() + "</p>");
        message.append("<p>" + "<b>" + "Contact tracing requirement: "  + "</b>"  + guidelineInputModel.getContactTracing() + "</p>");
        message.append("<p>" + "<b>" + "Contact tracing details: "  + "</b>"  + guidelineInputModel.getContactTracingDetails() + "</p>");
        message.append("<p>" + "<b>" + "Allowed group size: "  + "</b>"  + guidelineInputModel.getGroupSize() + "</p>");
        message.append("<p>" + "<b>" + "Group size details: "  + "</b>"  + guidelineInputModel.getGroupSizeDetails() + "</p>");
        message.append("<p>" + "<b>" + "Operating capacity: "  + "</b>"  + "<span style='color:#FF0000'>" + guidelineInputModel.getOpCapacity() + "</span>" + "</p>");
        message.append("<p>" + "<b>" + "Operating capacity details: "  + "</b>"  + guidelineInputModel.getOpCapacityDetails() + "</p>");
        message.append("<p>" + "<b>" + "Covid testing details: "  + "</b>"  + guidelineInputModel.getCovidTestingDetails() + "</p>");
        message.append("<p>" + "<b>" + "Vaccinated: "  + "</b>"  + guidelineInputModel.getCovidTestingVaccinated() + "</p>");
        message.append("<p>" + "<b>" + "Unvaccinated: "  + "</b>"  + guidelineInputModel.getCovidTestingUnvaccinated() + "</p>");

        String formedMessage = message.toString();
        emailToSend.setEmailContent(formedMessage);
        emailToSend.setEmailTo(email);
        sendEmail(emailToSend);
    }


}
