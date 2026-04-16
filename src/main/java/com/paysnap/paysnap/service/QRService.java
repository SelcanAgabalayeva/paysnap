package com.paysnap.paysnap.service;

public interface QRService {
    byte[] generate(String url) throws Exception;
}
