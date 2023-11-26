package com.aiary.aiary.domain.diary.repository;

import com.aiary.aiary.domain.diary.entity.DiaryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DiaryElasticsearchRepository extends ElasticsearchRepository<DiaryDocument, Long> {
}
