package com.example.bulatgraduatework.repo.institute;

import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepo extends JpaRepository<Group, Long> {
  List<Group> findAllByCourseAndIsDeletedIsFalseOrderByNum(Course course);
}
