package com.aiary.aiary.global.config;

import com.aiary.aiary.domain.diary.repository.DiaryElasticsearchRepository;
import com.aiary.aiary.domain.diary.repository.DiarySearchRepositoryImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.aiary.aiary.domain",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DiaryElasticsearchRepository.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DiarySearchRepositoryImpl.class)
        })
public class JpaAuditingConfig {}
