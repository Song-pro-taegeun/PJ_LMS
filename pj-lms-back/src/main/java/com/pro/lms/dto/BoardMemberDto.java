package com.pro.lms.dto;

import lombok.Data;

@Data
public class BoardMemberDto {
    private Long id;
    private String title;
    private String contents;
    private Long userid;
    private String username;
    private String email;
}
