package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Email;
import CovidLoveit.Repositories.Interfaces.GuidelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class EmailServiceImpl {

    private JavaMailSender javaMailSender;
    private final GuidelineRepository guidelineRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, GuidelineRepository guidelineRepository) {
        this.javaMailSender = javaMailSender;
        this.guidelineRepository = guidelineRepository;
    }


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

    public void guidelineEmail(String email, GuidelineInputModel guidelineInputModel) {
        Email emailToSend = new Email();

        emailToSend.setEmailFrom("TRAIL");

        StringBuilder message = new StringBuilder();

        //check if guideline already exists
        if ((guidelineInputModel.getGuidelineId()) == null) {
            emailToSend.setEmailSubject("New Guideline Announcement");
            message.append("Dear " + email + "," +
                            "<p>" + "The newly added guideline for your establishment: " + "</p>");
        } else {
            emailToSend.setEmailSubject("Guideline Update Announcement");
            message.append("Dear " + email + "," +
                    "<p>" + "Updated guideline for your establishment: " + "</p>");
        }

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


}
