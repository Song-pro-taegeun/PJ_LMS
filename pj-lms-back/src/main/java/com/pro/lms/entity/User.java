package com.pro.lms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    // user 테이블과 post는 일대다 관계임
    // user id와 종속된 Post 정보를 다 가져온다.
    // mappedBy는 해당 엔티티의 필드명 명시
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
}
