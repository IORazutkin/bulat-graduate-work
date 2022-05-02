package com.example.bulatgraduatework.api.user;

import com.example.bulatgraduatework.dto.UserDto;
import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.exception.UnprocessableEntityException;
import com.example.bulatgraduatework.service.LessonService;
import com.example.bulatgraduatework.service.institute.InstituteService;
import com.example.bulatgraduatework.service.user.RoleService;
import com.example.bulatgraduatework.service.user.TeacherService;
import com.example.bulatgraduatework.service.user.UserService;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
  private final TeacherService teacherService;
  private final InstituteService instituteService;
  private final UserService userService;
  private final RoleService roleService;
  private final LessonService lessonService;
  private final PasswordEncoder passwordEncoder;

  @JsonView(DataView.class)
  @GetMapping("institute/{instituteId}")
  public ResponseEntity<List<UserDto>> findAllByInstitute (@PathVariable String instituteId){
    Institute institute = instituteService.findById(Long.parseLong(instituteId));
    return ResponseEntity.ok(teacherService.findByInstitute(institute).stream().map(UserDto::new).collect(Collectors.toList()));
  }

  @JsonView(DataView.class)
  @PostMapping
  public ResponseEntity<UserDto> create (@RequestBody UserDto userDto) {
    User userFromDb = userService.findByUsername(userDto.getUsername());

    if (userFromDb != null) {
      throw new UnprocessableEntityException();
    }

    Institute institute = instituteService.findById(userDto.getInstitute().getId());
    User user = new User();
    user.setFullName(userDto.getFullName());
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRole(roleService.getTeacher());
    user = userService.create(user);
    List<Lesson> lessons = userDto.getLessons().stream()
      .map(item -> lessonService.findById(item.getId()))
      .collect(Collectors.toList());

    return ResponseEntity.ok(new UserDto(teacherService.create(user, institute, lessons)));
  }

  @JsonView(DataView.class)
  @PatchMapping("{id}")
  public ResponseEntity<UserDto> create (@PathVariable String id, @RequestBody UserDto userDto) {
    User userFromDb = userService.findByUsername(userDto.getUsername());

    if (userFromDb != null && userFromDb.getId() != Long.parseLong(id)) {
      throw new UnprocessableEntityException();
    }

    User user = userService.findById(Long.parseLong(id));
    user.setFullName(userDto.getFullName());
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user = userService.create(user);
    List<Lesson> lessons = userDto.getLessons().stream()
      .map(item -> lessonService.findById(item.getId()))
      .collect(Collectors.toList());

    return ResponseEntity.ok(new UserDto(teacherService.update(user, lessons)));
  }
}
