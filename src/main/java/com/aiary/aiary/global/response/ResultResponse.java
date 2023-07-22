package com.aiary.aiary.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {

    private String code;
    private String message;

    public static ResultResponse of(ResultCode resultCode){
        return new ResultResponse(resultCode);
    }

    public ResultResponse(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}