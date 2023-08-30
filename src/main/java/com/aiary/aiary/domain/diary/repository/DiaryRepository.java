package com.aiary.aiary.domain.diary.repository;

import com.aiary.aiary.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d JOIN d.user u WHERE u.id = :userId " +
            "AND d.isDeleted = false " +
            "AND FUNCTION('DATE_FORMAT', d.diaryDate, '%Y-%m') " +
            " = FUNCTION('DATE_FORMAT', :diaryDate, '%Y-%m')" +
            "order by d.diaryDate desc")
    List<Diary> findMonthlyDiaryByUserId(@Param("userId") Long userId, @Param("diaryDate") Date diaryDate);

    @Query(value = "SELECT d FROM Diary d JOIN FETCH d.user WHERE d.id = :diaryId AND d.isDeleted = false")
    Optional<Diary> findDiaryWithUser(@Param("diaryId") Long diaryId);
}