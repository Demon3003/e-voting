package com.team.app.backend.service;

import javax.mail.internet.MimeMessage;

import com.team.app.backend.persistance.model.Election;
import com.team.app.backend.persistance.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailsService {

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender mailSender;

    public SimpleMailMessage activationLetter(User user) {
        String subject = "Підтвердження реєстрації";
        String confirmationUrl = env.getProperty("application.url") 
            + "/api/user/activate?token=" 
            + user.getActivate_link();
        String message = "Привіт, " +user.getLastName()+" "+user.getFirstName()+"\n"+
                "Вітаємо у нашій системі електронного голосування!\n"+
                "Щоб підтвердити реєстрацію вашого акаунту, будь ласка, натисніть на наступне посилання:\n"+
                confirmationUrl+
                "\nДякуємо Вам за реєстрацію!\n"+
                "Всього найкращого,\n"+"ваша команда підтримки E-vote.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("E-vote");
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);
        return email;
    }

    public SimpleMailMessage recoveryPasswordEmail(User user,String password) {
        String subject = "Новий пароль";
        String message = "Привіт, " +user.getLastName()+" "+user.getFirstName()+"\n"+
                "Ти запросив відновлення пароля "+user.getUsername()+
                "\nТвій новий пароль: \n"+
                password+
                "\nВсього найкращого,\n"+" команда підтримки E-vote.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("E-vote");
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);
        return email;
    }

    public MimeMessage sentQrCode(User user, Election el, ByteArrayResource qr) throws Exception {
        String subject = "Запрошення на вибори!";
        String messageText = "Привіт, " +user.getLastName()+" "+user.getFirstName()+"\n"+
                "Тебе запрошено на "+el.getName()+
                "\nТвій QR-код для ідентифікації прікріплено до листа. \n"+
                "\nВсього найкращого,\n"+" команда підтримки E-vote.";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setFrom("E-vote");
        helper.setTo(user.getEmail());
        helper.setText(messageText, false);
        helper.addAttachment("invitation_" + user.getLastName() +  "_.png", qr);
    
        return message;
    }

}
