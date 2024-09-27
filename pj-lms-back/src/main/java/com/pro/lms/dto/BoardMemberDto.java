package com.pro.lms.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BoardMemberDto {
    private Long id;
    private String title;
    private String contents;
    private Long userid;
    private String username;
    private String email;

    @QueryProjection
    public BoardMemberDto(Long id, String title, String contents, Long userid, String username, String email) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userid = userid;
        this.username = username;
        this.email = email;
    }
}
