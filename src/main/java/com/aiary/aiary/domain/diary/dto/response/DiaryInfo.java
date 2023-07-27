package com.aiary.aiary.domain.diary.dto.response;

import com.aiary.aiary.domain.diary.entity.Weather;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryInfo {

    private String title;
    private Weather weather;
    private String emoji;
    private String contents;
    private String diaryDate;
    private String drawingUrl;

}
