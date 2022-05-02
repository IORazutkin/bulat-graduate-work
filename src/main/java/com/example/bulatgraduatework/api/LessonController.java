package com.example.bulatgraduatework.api;

import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.service.LessonService;
import com.example.bulatgraduatework.service.institute.CourseService;
import com.example.bulatgraduatework.service.institute.InstituteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {
  private final LessonService lessonService;
  private final CourseService courseService;
  private final InstituteService instituteService;

  @GetMapping("{id}")
  public ResponseEntity<Lesson> findById (@PathVariable String id) {
    return ResponseEntity.ok(lessonService.findById(Long.parseLong(id)));
  }

  @GetMapping("course/{courseId}")
  public ResponseEntity<List<Lesson>> findAllByCourse (@PathVariable String courseId) {
    Course course = courseService.findById(Long.parseLong(courseId));
    return ResponseEntity.ok(lessonService.findAllByCourse(course));
  }

  @GetMapping("institute/{instituteId}")
  public ResponseEntity<List<Lesson>> findAllByInstitute (@PathVariable String instituteId) {
    Institute institute = instituteService.findById(Long.parseLong(instituteId));
    return ResponseEntity.ok(lessonService.findAllByInstitute(institute));
  }

  @PostMapping
  public ResponseEntity<Lesson> create (@RequestBody Lesson lesson) {
    return ResponseEntity.ok(lessonService.create(lesson));
  }

  @PatchMapping("{id}")
  public ResponseEntity<Lesson> update (@PathVariable String id, @RequestBody Lesson lesson) {
    return ResponseEntity.ok(lessonService.update(Long.parseLong(id), lesson));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete (@PathVariable String id) {
    lessonService.delete(Long.parseLong(id));
    return ResponseEntity.ok().build();
  }
}
