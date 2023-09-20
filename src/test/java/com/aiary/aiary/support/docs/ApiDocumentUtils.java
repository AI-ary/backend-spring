package com.aiary.aiary.support.docs;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
public interface ApiDocumentUtils {

    // OperationRequestPreprocessor 는 API 문서화를 위해 요청에 대한 사전처리를 수행
    // prettyPrint() 메서드는 요청/응답을 보기 좋게 만듬.
    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(prettyPrint());
    }

    // OperationResponsePreprocessor 는 API 문서화를 위해 응답에 대한 사전처리를 수행
    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }
}
