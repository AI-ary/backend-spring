package com.aiary.aiary.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Theme {
    ORIGINAL("기본"),
    BLUE("파랑"),
    RAINBOW("무지개");

    private final String value;

}
