package com.aiary.aiary.domain.user.entity;


import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.global.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Column(nullable = true, length = 250)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Theme theme;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_active")
    private boolean isActive = true; // 활성 여부

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Diary> diaries = new ArrayList<>();

    @Builder
    private User(String email, String nickname, String password, String profileImage, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.profileImage = null;
        this.theme = Theme.ORIGINAL;
    }

    public void inActive() {
        this.isActive = false;
    }

    public void setEncryptedPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateTheme(String theme) {
        this.theme = Theme.valueOf(theme);
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
