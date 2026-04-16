package com.paysnap.paysnap.service.impls;

import com.paysnap.paysnap.entity.Order;
import com.paysnap.paysnap.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendReceiptEmail(String toEmail, Order order, byte[] pdf) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("PaySnap Payment Receipt - Order #" + order.getId());

            String body =
                    "Hello,\n\n" +
                            "Your payment was successful.\n" +
                            "Order ID: " + order.getId() + "\n" +
                            "Amount: " + order.getAmount() + " " + order.getCurrency() + "\n\n" +
                            "Thank you for using PaySnap!";

            helper.setText(body);

            // PDF attachment
            helper.addAttachment(
                    "receipt-" + order.getId() + ".pdf",
                    new ByteArrayResource(pdf)
            );

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }
}
