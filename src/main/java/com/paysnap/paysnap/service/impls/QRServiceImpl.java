package com.paysnap.paysnap.service.impls;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.paysnap.paysnap.service.QRService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QRServiceImpl implements QRService {

    @Override
    public byte[] generate(String url) throws Exception {

        BitMatrix matrix = new MultiFormatWriter()
                .encode(url, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);

        return out.toByteArray();
    }
}
