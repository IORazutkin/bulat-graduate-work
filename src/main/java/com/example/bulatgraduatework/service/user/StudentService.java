package com.example.bulatgraduatework.service.user;

import com.example.bulatgraduatework.dto.UserDto;
import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.Student;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.entity.user.UserPK;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.user.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {
  private final StudentRepo studentRepo;

  public Student findByUser (User user) {
    Optional<Student> studentOptional = studentRepo.findById(new UserPK(user));

    if (studentOptional.isPresent()) {
      return studentOptional.get();
    }

    throw new NotFoundException();
  }

  public List<Student> findByInstitute (Institute institute) {
    return studentRepo.findAllByGroup_Course_InstituteAndUser_User_IsDeletedIsFalse(institute);
  }

  public Map<String, List<UserDto>> findByCourse (Course course) {
    List<Student> studentList = studentRepo.findAllByGroup_CourseAndUser_User_IsDeletedIsFalse(course);
    Map<String, List<UserDto>> result = new HashMap<>();

    studentList.forEach(student -> {
      String groupNum = student.getGroup().getNum();

      if (!result.containsKey(groupNum)) {
        result.put(groupNum, new ArrayList<>());
      }

      result.get(groupNum).add(new UserDto(student));
    });

    return result;
  }

  public Student create (User user, Group group) {
    Student student = new Student();
    student.setUser(new UserPK(user));
    student.setGroup(group);
    return studentRepo.save(student);
  }

  public Student update (User user, Group group) {
    Student student = findByUser(user);
    student.setGroup(group);
    return studentRepo.save(student);
  }
}
