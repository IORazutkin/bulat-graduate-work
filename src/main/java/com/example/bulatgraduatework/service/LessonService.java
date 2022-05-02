package com.example.bulatgraduatework.service;

import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.LessonRepo;
import com.example.bulatgraduatework.util.CopyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonService {
  private final LessonRepo lessonRepo;

  public List<Lesson> findAllByCourse (Course course) {
    return lessonRepo.findAllByCourseAndIsDeletedIsFalseOrderByTitle(course);
  }

  public List<Lesson> findAllByInstitute (Institute institute) {
    return lessonRepo.findAllByCourse_InstituteAndIsDeletedIsFalseOrderByTitle(institute);
  }

  public Lesson findById (Long id) {
    Optional<Lesson> lessonOptional = lessonRepo.findById(id);

    if (lessonOptional.isPresent()) {
      return lessonOptional.get();
    }

    throw new NotFoundException();
  }

  public Lesson create (Lesson lesson) {
    return lessonRepo.save(lesson);
  }

  public Lesson update (Long id, Lesson lesson) {
    Lesson lessonFromDb = findById(id);
    CopyProperties.copy(lesson, lessonFromDb, "id", "course");
    return lessonRepo.save(lessonFromDb);
  }

  public void delete (Long id) {
    Lesson lesson = findById(id);
    lesson.setIsDeleted(true);
    lessonRepo.save(lesson);
  }
}
