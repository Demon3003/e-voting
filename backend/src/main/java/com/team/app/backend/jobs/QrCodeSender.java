package com.team.app.backend.jobs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;

import net.glxn.qrgen.javase.QRCode;
import com.team.app.backend.persistance.model.Election;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.repositories.ElectionRepo;
import com.team.app.backend.service.EmailsService;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@Component
@Slf4j
public class QrCodeSender {

    @Autowired
    private EmailsService emailsService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    ElectionRepo electionRepo;

    private DateTime currentDate = new DateTime();


    public List<Election> findElectionsToday() {
        List<Election> elections = electionRepo.findAll();
        elections = elections.stream()
            .filter(el -> DateTimeComparator.getDateOnlyInstance()
                .compare(new DateTime(el.getStartDate()), DateTime.now()) == 0)
            .collect(Collectors.toList());

        return elections;

    }

    //every 100 seconds
    @Scheduled(fixedDelay = 100000)
    public void sendMail() throws Exception {

        if (DateTimeComparator.getDateOnlyInstance().compare(currentDate, DateTime.now()) == 0) {

            List<Election> elections = findElectionsToday();
            for (Election el :  elections) {
                List<User> users = el.getUsers();
                for (User user : users) {
                    String qrText = "{'userId':" + user.getId() + ", 'electionId':" + el.getId() + "}"; 
                    log.info(qrText);
                    MimeMessage message = emailsService.sentQrCode(user, el, generateQRCodeImage(qrText));
                    mailSender.send(message);
                }
            }
            currentDate = new DateTime().plusDays(1);

        }

    }

    public static ByteArrayResource generateQRCodeImage(String barcodeText) throws Exception {
        ByteArrayOutputStream stream = QRCode
          .from(barcodeText)
          .withSize(250, 250)
          .stream();
        // ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
        
        return new ByteArrayResource(stream.toByteArray());
    }
    
}
