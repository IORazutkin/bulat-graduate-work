package com.example.bulatgraduatework.api.institute;

import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.repo.institute.GroupRepo;
import com.example.bulatgraduatework.service.institute.CourseService;
import com.example.bulatgraduatework.service.institute.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {
  private final GroupService groupService;
  private final CourseService courseService;

  @GetMapping("course/{courseId}")
  public ResponseEntity<List<Group>> findAllByCourse (@PathVariable String courseId) {
    Course course = courseService.findById(Long.parseLong(courseId));
    return ResponseEntity.ok(groupService.findAllByCourse(course));
  }

  @PostMapping
  public ResponseEntity<Group> findAll (@RequestBody Group group) {
    return ResponseEntity.ok(groupService.create(group));
  }

  @PatchMapping("{id}")
  public ResponseEntity<Group> update (@PathVariable String id, @RequestBody Group group) {
    return ResponseEntity.ok(groupService.update(Long.parseLong(id), group));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete (@PathVariable String id) {
    groupService.delete(Long.parseLong(id));
    return ResponseEntity.ok().build();
  }
}
