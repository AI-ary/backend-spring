package com.aiary.aiary.domain.diary.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchDiariesRes {

    private List<DiaryRes> diaryRes;
    private int curPageNumber;
    private boolean hasNext;
    private boolean hasPrevious;

}
