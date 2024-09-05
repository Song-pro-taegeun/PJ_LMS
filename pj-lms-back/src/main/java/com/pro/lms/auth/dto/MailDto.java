package com.pro.lms.auth.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailDto {
    // 수신자
    private String to;

    // 제목
    private String subject;

    // 메시지
    private String message;
    private Boolean isHtml;
}
