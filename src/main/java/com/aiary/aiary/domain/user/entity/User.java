package com.aiary.aiary.domain.user.entity;


import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false, length = 250)
    private String password;

    @Column(nullable = false, length = 500)
    private String thema;   // 테마 (변경될 수 있음)

    @Column(name = "is_active")
    private boolean isActive = true; // 활성 여부

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();

    @Builder
    private User(String email, String nickname, String password, String thema) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.thema = thema;
    }

    public void inactive() {
        this.isActive = false;
    }
}
