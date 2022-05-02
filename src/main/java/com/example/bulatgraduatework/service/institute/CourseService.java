package com.example.bulatgraduatework.service.institute;

import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.institute.CourseRepo;
import com.example.bulatgraduatework.repo.institute.InstituteRepo;
import com.example.bulatgraduatework.util.CopyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
  private final CourseRepo courseRepo;

  public List<Course> findAllByInstitute (Institute institute) {
    return courseRepo.findAllByInstituteAndIsDeletedIsFalseOrderByTitle(institute);
  }

  public Course findById (Long id) {
    Optional<Course> courseOptional = courseRepo.findById(id);

    if (courseOptional.isPresent()) {
      return courseOptional.get();
    }

    throw new NotFoundException();
  }

  public Course save (Course course) {
    return courseRepo.save(course);
  }

  public Course update (Long id, Course course) {
    Course courseFromDb = findById(id);
    CopyProperties.copy(course, courseFromDb, "id", "institute");

    return courseRepo.save(courseFromDb);
  }

  public void deleteById (Long id) {
    Course course = findById(id);
    course.setIsDeleted(true);
    courseRepo.save(course);
  }
}
