package com.example.bulatgraduatework.repo.user;

import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.Student;
import com.example.bulatgraduatework.entity.user.UserPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface StudentRepo extends JpaRepository<Student, UserPK> {
  List<Student> findAllByGroup(Group group);
  List<Student> findAllByGroup_CourseAndUser_User_IsDeletedIsFalse(Course course);
  List<Student> findAllByGroup_Course_InstituteAndUser_User_IsDeletedIsFalse(Institute institute);
}
