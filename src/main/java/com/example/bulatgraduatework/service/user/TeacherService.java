package com.example.bulatgraduatework.service.user;

import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.Teacher;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.entity.user.UserPK;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.user.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {
  private final TeacherRepo teacherRepo;

  public Teacher findByUser (User user) {
    Optional<Teacher> teacherOptional = teacherRepo.findById(new UserPK(user));

    if (teacherOptional.isPresent()) {
      return teacherOptional.get();
    }

    throw new NotFoundException();
  }

  public List<Teacher> findByInstitute (Institute institute) {
    return teacherRepo.findAllByInstituteAndUser_User_IsDeletedIsFalse(institute);
  }

  public Teacher create (User user, Institute institute, List<Lesson> lessons) {
    Teacher teacher = new Teacher();
    teacher.setUser(new UserPK(user));
    teacher.setInstitute(institute);
    teacher.setLessons(lessons);

    return teacherRepo.save(teacher);
  }

  public Teacher update (User user, List<Lesson> lessons) {
    Teacher teacher = findByUser(user);
    teacher.setLessons(lessons);

    return teacherRepo.save(teacher);
  }
}
