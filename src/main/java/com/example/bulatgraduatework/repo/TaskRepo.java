package com.example.bulatgraduatework.repo;

import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.Task;
import com.example.bulatgraduatework.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
  List<Task> findAllByLessonAndIsDeletedIsFalse(Lesson lesson);
  List<Task> findAllByStudentListContainsAndIsDeletedIsFalse(Student student);
}
