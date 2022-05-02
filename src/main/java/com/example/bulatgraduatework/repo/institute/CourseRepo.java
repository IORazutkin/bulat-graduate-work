package com.example.bulatgraduatework.repo.institute;

import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Long> {
  List<Course> findAllByInstituteAndIsDeletedIsFalseOrderByTitle(Institute institute);
}
