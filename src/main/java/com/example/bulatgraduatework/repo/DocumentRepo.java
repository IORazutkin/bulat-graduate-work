package com.example.bulatgraduatework.repo;

import com.example.bulatgraduatework.entity.Document;
import com.example.bulatgraduatework.entity.Task;
import com.example.bulatgraduatework.entity.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepo extends JpaRepository<Document, Long> {
  List<Document> findAllByTaskAndIsDeletedIsFalse(Task task);
  List<Document> findAllByIsDeletedIsFalseAndVerifiedStatusIsNull();
  List<Document> findAllByIsDeletedIsFalseAndTaskIsNull();
  Integer countAllByTask_Teacher(Teacher teacher);
}
