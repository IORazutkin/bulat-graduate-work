package com.example.bulatgraduatework.api.user;

import com.example.bulatgraduatework.dto.UserDto;
import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.exception.UnprocessableEntityException;
import com.example.bulatgraduatework.service.institute.CourseService;
import com.example.bulatgraduatework.service.institute.GroupService;
import com.example.bulatgraduatework.service.institute.InstituteService;
import com.example.bulatgraduatework.service.user.RoleService;
import com.example.bulatgraduatework.service.user.StudentService;
import com.example.bulatgraduatework.service.user.UserService;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {
  private final StudentService studentService;
  private final InstituteService instituteService;
  private final GroupService groupService;
  private final UserService userService;
  private final RoleService roleService;
  private final CourseService courseService;
  private final PasswordEncoder passwordEncoder;

  @JsonView(DataView.class)
  @GetMapping("institute/{instituteId}")
  public ResponseEntity<List<UserDto>> findAllByInstitute (@PathVariable String instituteId){
    Institute institute = instituteService.findById(Long.parseLong(instituteId));
    return ResponseEntity.ok(studentService.findByInstitute(institute).stream().map(UserDto::new).collect(Collectors.toList()));
  }

  @JsonView(DataView.class)
  @GetMapping("course/{courseId}")
  public ResponseEntity<Map<String, List<UserDto>>> findByCourseGroupByGroup (
    @PathVariable String courseId
  ) {
    Course course = courseService.findById(Long.parseLong(courseId));
    return ResponseEntity.ok(studentService.findByCourse(course));
  }

  @JsonView(DataView.class)
  @PostMapping
  public ResponseEntity<UserDto> create (@RequestBody UserDto userDto) {
    User userFromDb = userService.findByUsername(userDto.getUsername());

    if (userFromDb != null) {
      throw new UnprocessableEntityException();
    }

    Group group = groupService.findById(userDto.getGroup().getId());
    User user = new User();
    user.setFullName(userDto.getFullName());
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRole(roleService.getStudent());
    user = userService.create(user);

    return ResponseEntity.ok(new UserDto(studentService.create(user, group)));
  }

  @JsonView(DataView.class)
  @PatchMapping("{id}")
  public ResponseEntity<UserDto> create ( @PathVariable String id, @RequestBody UserDto userDto) {
    User userFromDb = userService.findByUsername(userDto.getUsername());

    if (userFromDb != null && userFromDb.getId() != Long.parseLong(id)) {
      throw new UnprocessableEntityException();
    }

    Group group = groupService.findById(userDto.getGroup().getId());
    User user = userService.findById(Long.parseLong(id));
    user.setFullName(userDto.getFullName());
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRole(roleService.getStudent());
    user = userService.create(user);

    return ResponseEntity.ok(new UserDto(studentService.update(user, group)));
  }
}
