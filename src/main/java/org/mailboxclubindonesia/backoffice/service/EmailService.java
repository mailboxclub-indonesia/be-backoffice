package org.mailboxclubindonesia.backoffice.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.mailboxclubindonesia.backoffice.config.EmailConfig;
import org.mailboxclubindonesia.backoffice.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private TemplateEngine templateEngine;

  private final EmailConfig emailConfig;

  public EmailService(EmailConfig emailConfig) {
    this.emailConfig = emailConfig;
  }

  public void sendEmailServiceDiagnostic() {
    SimpleMailMessage mail = new SimpleMailMessage();

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE, dd MMM yyyy HH:mm:ss zzzz");
    Date date = new Date();

    mail.setTo("reizkian@mailboxclubindonesia.org");
    mail.setFrom("backoffice@mailboxclubindonesia.org");
    mail.setSubject("Backoffice Email Service Diagnostic");
    mail.setText(dateFormat.format(date) + "\nMailbox Club Indonesia Backoffice Server");

    mailSender.send(mail);
  }

  public void sendEmailAuthRegister(String setTo, UserDetail userDetail) throws MessagingException {
    Context context = new Context();
    context.setVariable("fullName", userDetail.getFullName());

    String htmlContent = templateEngine.process("auth-register", context);
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

    helper.setTo(setTo);
    helper.setFrom(this.emailConfig.getUsername());
    helper.setSubject("Registrasi Akun");
    helper.setText(htmlContent, true);

    mailSender.send(mimeMessage);
  }
}
