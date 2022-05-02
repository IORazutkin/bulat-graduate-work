package com.example.bulatgraduatework.repo.institute;

import com.example.bulatgraduatework.entity.institute.Institute;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituteRepo extends JpaRepository<Institute, Long> {
  List<Institute> findAllByIsDeletedIsFalse();
  List<Institute> findAllByIsDeletedIsFalse(Sort sort);
}
