package com.example.bulatgraduatework.api.institute;

import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.service.institute.CourseService;
import com.example.bulatgraduatework.service.institute.InstituteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
  private final CourseService courseService;
  private final InstituteService instituteService;

  @GetMapping("{id}")
  public ResponseEntity<Course> findById (@PathVariable String id){
    return ResponseEntity.ok(courseService.findById(Long.parseLong(id)));
  }

  @GetMapping("institute/{instituteId}")
  public ResponseEntity<List<Course>> findAllByInstitute (@PathVariable String instituteId){
    Institute institute = instituteService.findById(Long.parseLong(instituteId));
    return ResponseEntity.ok(courseService.findAllByInstitute(institute));
  }

  @PostMapping
  public ResponseEntity<Course> create (@RequestBody Course course) {
    Institute institute = instituteService.findById(course.getInstitute().getId());
    course.setInstitute(institute);
    return ResponseEntity.ok(courseService.save(course));
  }

  @PatchMapping("{id}")
  public ResponseEntity<Course> update (@PathVariable String id, @RequestBody Course course) {
    return ResponseEntity.ok(courseService.update(Long.parseLong(id), course));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete (@PathVariable String id) {
    courseService.deleteById(Long.parseLong(id));
    return ResponseEntity.ok().build();
  }
}
