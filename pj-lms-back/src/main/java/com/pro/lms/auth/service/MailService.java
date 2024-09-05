package com.pro.lms.auth.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    String mailSend(String pmUserId);
}
