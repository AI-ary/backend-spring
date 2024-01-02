package com.aiary.aiary.domain.diary.entity;

import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.global.entity.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Diaries")
public class Diary extends BaseEntity {

    @Id
    @Column(name = "diary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Weather weather;

    @Column(nullable = false)
    private String emoji;

    @Column(name = "drawing_url", length = 500)
    private String drawingUrl;

    @Column(nullable = false, length = 50)
    private String contents;

    @Column(name = "diary_date", nullable = false)
    private LocalDate diaryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Diary(String title, Weather weather, String emoji, String drawingUrl,
        String contents, LocalDate diaryDate, User user) {
        this.title = title;
        this.weather = weather;
        this.emoji = emoji;
        this.drawingUrl = drawingUrl;
        this.contents = contents;
        this.diaryDate = diaryDate;
        this.user = user;
    }
}
