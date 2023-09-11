package com.aiary.aiary.domain.diary.entity;

import com.aiary.aiary.domain.diary.dto.request.DiaryUpdateRequest;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "drawing_date", nullable = false)
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

    public void updateDiary(DiaryUpdateRequest request, String drawingUrl) {
        this.title = request.getTitle();
        this.weather = Weather.valueOf(request.getWeather());
        this.emoji = request.getEmoji();
        this.drawingUrl = drawingUrl;
        this.contents = request.getContents();
    }
}
