package com.aiary.aiary.domain.diary.dto.response;

import com.aiary.aiary.domain.diary.entity.Weather;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiaryRes {

    private Long diaryId;
    private String title;
    private Weather weather;
    private String emoji;
    private String contents;
    private String diaryDate;
    private String drawingUrl;

}
