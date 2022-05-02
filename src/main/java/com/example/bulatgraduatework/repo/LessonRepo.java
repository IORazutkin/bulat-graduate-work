package com.example.bulatgraduatework.repo;

import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepo extends JpaRepository<Lesson, Long> {
  List<Lesson> findAllByCourseAndIsDeletedIsFalseOrderByTitle(Course course);
  List<Lesson> findAllByCourse_InstituteAndIsDeletedIsFalseOrderByTitle(Institute institute);
}
