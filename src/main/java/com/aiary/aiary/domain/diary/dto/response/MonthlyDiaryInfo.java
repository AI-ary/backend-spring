package com.aiary.aiary.domain.diary.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthlyDiaryInfo {
    List<DiaryInfo> monthlyDiaryInfo = new ArrayList<>(32);
}
