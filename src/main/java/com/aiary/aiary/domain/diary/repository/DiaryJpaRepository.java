package com.aiary.aiary.domain.diary.repository;

import com.aiary.aiary.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryJpaRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d JOIN d.user u WHERE u.id = :userId " +
            "AND d.isDeleted = false " +
            "AND FUNCTION('DATE_FORMAT', d.diaryDate, '%Y-%m') " +
            " = FUNCTION('DATE_FORMAT', :monthDate, '%Y-%m')" +
            "order by d.diaryDate desc")
    List<Diary> findMonthlyDiaryByUserId(@Param("userId") Long userId, @Param("monthDate") LocalDate diaryDate);

    @Query(value = "SELECT d FROM Diary d JOIN FETCH d.user WHERE d.id = :diaryId AND d.isDeleted = false")
    Optional<Diary> findDiaryWithUser(@Param("diaryId") Long diaryId);
}