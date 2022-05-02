package com.example.bulatgraduatework.repo.user;

import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.Deanery;
import com.example.bulatgraduatework.entity.user.Teacher;
import com.example.bulatgraduatework.entity.user.UserPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepo extends JpaRepository<Teacher, UserPK> {
  List<Teacher> findAllByInstituteAndUser_User_IsDeletedIsFalse(Institute institute);
}
