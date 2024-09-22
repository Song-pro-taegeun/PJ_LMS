package com.pro.lms.auth.service;

import com.pro.lms.auth.dto.MailDto;
import com.pro.lms.repository.LmsMemberRepository;
import com.pro.lms.util.AesCipher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private static final String EXAMPLE_LINK_TEMPLATE = "MailTemplate.html";
    private final JavaMailSender mailSender;

    @Autowired
    private AesCipher aesCipher;

    @Autowired
    private LmsMemberRepository lmsMemberRepository;

    private static String code;

    public String mailSend(String pmUserId) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Context context = getContext(pmUserId);

        String message = templateEngine.process(EXAMPLE_LINK_TEMPLATE, context);
        MailDto emailMessage = MailDto.builder().to(pmUserId)
                .subject("Learning Management System Code").message(message).isHtml(true).build();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());

            //html 템플릿으로 보낼거면 true plaintext로 보낼거면 false
            mimeMessageHelper.setText(emailMessage.getMessage(), true);

            mailSender.send(mimeMessage);
            log.info("sent email: {}", emailMessage.getMessage());
            return aesCipher.encrypt(code);

        } catch (MessagingException e) {
            log.error("[EmailService.send()] error {}", e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Context getContext(String pmUserId) {
        Context context = new Context();
        context.setVariable("pmUserId", pmUserId);
        context.setVariable("tempCode", createCode());
        return context;
    }

    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            code = builder.toString();
            return code;
        } catch (NoSuchAlgorithmException e) {
            log.debug("createCode() exception occur", e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String emailCheck(String email) {
        Boolean exists = lmsMemberRepository.existsByPmUserId(email);
        System.out.println(exists);
        if(exists) {
            return "1";
        }
        else {
            return mailSend(email);
        }
    }
}
