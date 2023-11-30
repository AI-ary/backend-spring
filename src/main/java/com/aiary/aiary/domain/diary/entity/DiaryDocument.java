package com.aiary.aiary.domain.diary.entity;

import com.aiary.aiary.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;
import java.time.Instant;

@Document(indexName = "diary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryDocument extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String weather;
    private String emoji;
    private String drawingUrl;
    private String contents;
    private Instant diaryDate;  // Elasticsearch 의 날짜타입과 일치하도록
    private Long userId;

    @Builder
    public DiaryDocument(String title, String weather, String emoji, String drawingUrl,
                 String contents, Instant diaryDate, Long userId) {
        this.title = title;
        this.weather = weather;
        this.emoji = emoji;
        this.drawingUrl = drawingUrl;
        this.contents = contents;
        this.diaryDate = diaryDate;
        this.userId = userId;
    }
}
