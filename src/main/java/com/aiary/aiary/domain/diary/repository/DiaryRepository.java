package com.aiary.aiary.domain.diary.repository;

import com.aiary.aiary.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d JOIN d.user u WHERE u.id = :userId " +
            "AND d.isDeleted = false " +
            "AND FUNCTION('DATE_FORMAT', d.diaryDate, '%Y-%m') " +
            " = FUNCTION('DATE_FORMAT', :diaryDate, '%Y-%m')" +
            "order by d.diaryDate desc")
    List<Diary> findMonthlyDiaryByUserId(@Param("userId") Long userId, @Param("diaryDate") Date diaryDate);
}