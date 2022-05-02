package com.example.bulatgraduatework.api;

import com.example.bulatgraduatework.dto.TaskDto;
import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.user.Student;
import com.example.bulatgraduatework.entity.user.Teacher;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.service.LessonService;
import com.example.bulatgraduatework.service.TaskService;
import com.example.bulatgraduatework.service.user.StudentService;
import com.example.bulatgraduatework.service.user.TeacherService;
import com.example.bulatgraduatework.service.user.UserService;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
  private final LessonService lessonService;
  private final TaskService taskService;
  private final TeacherService teacherService;
  private final StudentService studentService;
  private final UserService userService;

  @JsonView(DataView.class)
  @GetMapping("{id}")
  public ResponseEntity<TaskDto> findById (@PathVariable String id) {
    return ResponseEntity.ok(new TaskDto(taskService.findById(Long.parseLong(id))));
  }

  @JsonView(DataView.class)
  @GetMapping("lesson/{lessonId}")
  public ResponseEntity<List<TaskDto>> findByLesson (@PathVariable String lessonId) {
    Lesson lesson = lessonService.findById(Long.parseLong(lessonId));
    return ResponseEntity.ok(taskService.findByLesson(lesson).stream().map(TaskDto::new).collect(Collectors.toList()));
  }

  @JsonView(DataView.class)
  @GetMapping("student/{userId}")
  public ResponseEntity<List<TaskDto>> findByStudent (@PathVariable String userId) {
    User user = userService.findById(Long.parseLong(userId));
    Student student = studentService.findByUser(user);
    return ResponseEntity.ok(taskService.findByStudent(student).stream().map(TaskDto::new).collect(Collectors.toList()));
  }

  @JsonView(DataView.class)
  @PostMapping
  public ResponseEntity<TaskDto> create (
    @AuthenticationPrincipal User auth,
    @RequestBody TaskDto taskDto
  ) {
    Lesson lesson = lessonService.findById(taskDto.getLesson().getId());
    Teacher teacher = teacherService.findByUser(auth);
    List<Student> studentList = taskDto.getStudents().stream()
      .map(studentService::findByUser)
      .collect(Collectors.toList());

    return ResponseEntity.ok(new TaskDto(taskService.create(taskDto, lesson, teacher, studentList)));
  }

  @JsonView(DataView.class)
  @PatchMapping("{id}")
  public ResponseEntity<TaskDto> create (
    @AuthenticationPrincipal User auth,
    @PathVariable String id,
    @RequestBody TaskDto taskDto
  ) {
    List<Student> studentList = taskDto.getStudents().stream()
      .map(studentService::findByUser)
      .collect(Collectors.toList());

    return ResponseEntity.ok(new TaskDto(taskService.update(Long.parseLong(id), taskDto, studentList)));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete (@PathVariable String id) {
    taskService.deleteById(Long.parseLong(id));
    return ResponseEntity.ok().build();
  }
}
