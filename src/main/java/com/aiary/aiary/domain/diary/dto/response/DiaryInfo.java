package com.aiary.aiary.domain.diary.dto.response;

import com.aiary.aiary.domain.diary.entity.Weather;
import lombok.*;

import java.sql.Date;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryInfo {

    private String title;
    private Weather weather;
    private String emoji;
    private String contents;
    private Date diaryDate;
    private String drawingUrl;

}
