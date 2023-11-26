package com.aiary.aiary.domain.diary.repository;

import com.aiary.aiary.domain.diary.entity.DiaryDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
@Slf4j
public class DiarySearchRepositoryImpl {

    private final ElasticsearchOperations elasticsearchOperations;
    private Query buildSearchQuery(Long userId, LocalDate monthDate, String keyword, Pageable pageable) {
        return new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("userId", userId))
                        .mustNot(QueryBuilders.termQuery("isDeleted", true))
                        .must(QueryBuilders.queryStringQuery("*" + keyword + "*")
                                .field("title").field("contents"))
                        .must(QueryBuilders.rangeQuery("diaryDate")
                                .from(monthDate.withDayOfMonth(1).toString())
                                .to(monthDate.withDayOfMonth(monthDate.lengthOfMonth()).toString())
                        )
                )
                .withPageable(pageable)
                .withSorts(SortBuilders.fieldSort("diaryDate").order(SortOrder.DESC))
                .build();
    }

    public Page<DiaryDocument> searchByKeyword(Long userId, LocalDate monthDate, String keyword, Pageable pageable) {
        Query query = buildSearchQuery(userId, monthDate, keyword, pageable);
        SearchHits<DiaryDocument> search = elasticsearchOperations.search(query, DiaryDocument.class);
        log.info("결과: {}", search);
        return new PageImpl<>(search.get().map(SearchHit::getContent).collect(Collectors.toList()), pageable, search.getTotalHits());
    }
}
