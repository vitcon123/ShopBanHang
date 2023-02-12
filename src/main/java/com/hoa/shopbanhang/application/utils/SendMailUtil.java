package com.hoa.shopbanhang.application.utils;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;

public class SendMailUtil {

  private static final JavaMailSender mailSender;

  static {
    mailSender = BeanUtil.getBean(JavaMailSender.class);
  }

  public static void sendMailSimple(String toEmail, String body, String subject) {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(toEmail);
    message.setText(body);
    message.setSubject(subject);

    mailSender.send(message);
  }

  public static void sendMailAttachments(String to, String subject, String body, List<MultipartFile> files) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(body);

    JSONArray fileList = new JSONArray();
    if (files != null && files.size() > 0) {
      for (MultipartFile file : files) {
        JSONObject json = new JSONObject();
        json.put("path", FileUtil.uploadFile(file));
        helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
        fileList.add(json);
      }
    }

    mailSender.send(message);

//    CreateMailLogInput createMailLogInput = new CreateMailLogInput();
//    createMailLogInput.setTo(to);
//    createMailLogInput.setContent(fileList.toString());

//    try {
//      mailSender.send(message);
//      createMailLogInput.setIsSuccess(true);
//    } catch (Exception ex) {
//      createMailLogInput.setIsSuccess(false);
//      createMailLogInput.setDetailError(ex.getMessage());
//      ex.printStackTrace();
//    }
//    createMailLogInteractor.handle(createMailLogInput);
  }

  public static void sendMailWithHtml() {

  }
}
