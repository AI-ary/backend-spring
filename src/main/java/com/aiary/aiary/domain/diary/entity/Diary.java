package com.aiary.aiary.domain.diary.entity;

import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Diary")
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Weather weather;

    @Column(nullable = false, length = 5)
    private String emoji;

    @Column(length = 500)
    private String drawing_url;

    @Column(nullable = false, length = 50)
    private String contents;

    @Column(nullable = false)
    private Date diary_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Diary(String title, Weather weather, String emoji, String drawing_url,
                 String contents, Date diary_date, User user) {
        this.title = title;
        this.weather = weather;
        this.emoji = emoji;
        this.drawing_url = drawing_url;
        this.contents = contents;
        this.diary_date = diary_date;
        this.user = user;
    }
}
