package com.pro.lms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "USER_ID")
    private Long userId;

    // Jackson 라이브러리가 field를 직렬화하는 과정에서
    // 순환 참조 문제가 발생하여 스택 트레이스가 무한히 반복.
    // JPA 직렬화 문제 발생함(재귀적으로 계속 무는 현상)
    // 해결을 위해 @JsonIgnore 어노테이션 명시
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;
}
