package com.paysnap.paysnap.service.impls;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.paysnap.paysnap.entity.Order;
import com.paysnap.paysnap.service.EmailService;
import com.paysnap.paysnap.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final EmailService emailService;

    @Override
    public byte[] generate(Order order) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        doc.add(new Paragraph("PAYSNAP RECEIPT"));
        doc.add(new Paragraph("Order ID: " + order.getId()));
        doc.add(new Paragraph("Amount: " + order.getAmount()));
        doc.add(new Paragraph("Currency: " + order.getCurrency()));
        doc.add(new Paragraph("Status: " + order.getStatus()));

        doc.close();

        byte[] pdfBytes = out.toByteArray();

        // 📧 EMAIL GÖNDƏR
        emailService.sendReceiptEmail(
                order.getUser().getEmail(),
                order,
                pdfBytes
        );

        return pdfBytes;
    }
}
