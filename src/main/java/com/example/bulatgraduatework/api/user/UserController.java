package com.example.bulatgraduatework.api.user;

import com.example.bulatgraduatework.dto.PasswordDto;
import com.example.bulatgraduatework.dto.UserDto;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.exception.UnprocessableEntityException;
import com.example.bulatgraduatework.service.user.DeaneryService;
import com.example.bulatgraduatework.service.user.StudentService;
import com.example.bulatgraduatework.service.user.TeacherService;
import com.example.bulatgraduatework.service.user.UserService;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
  private final StudentService studentService;
  private final TeacherService teacherService;
  private final UserService userService;
  private final DeaneryService deaneryService;
  private final PasswordEncoder passwordEncoder;

  @JsonView(DataView.class)
  @GetMapping
  public ResponseEntity<UserDto> getAuthorizationUser(@AuthenticationPrincipal User auth){
    switch (auth.getRole().getName()) {
      case "TEACHER":
        return ResponseEntity.ok(new UserDto(teacherService.findByUser(auth)));
      case "STUDENT":
        return ResponseEntity.ok(new UserDto(studentService.findByUser(auth)));
      case "DEANERY":
        return ResponseEntity.ok(new UserDto(deaneryService.findByUser(auth)));
    }

    return ResponseEntity.ok(new UserDto(auth));
  }

  @JsonView(DataView.class)
  @PostMapping("/avatar")
  public ResponseEntity<User> addAvatar (
    @AuthenticationPrincipal User auth,
    @RequestParam MultipartFile file
  ) throws IOException {
    return ResponseEntity.ok(userService.updateAvatar(auth, file));
  }

  @JsonView(DataView.class)
  @PatchMapping
  public ResponseEntity<User> updateProfile (
    @AuthenticationPrincipal User auth,
    @RequestBody UserDto userDto
  ) {
    User userFromDb = userService.findByUsername(userDto.getUsername());

    if (userFromDb != null && !userFromDb.getId().equals(auth.getId())) {
      throw new UnprocessableEntityException();
    }

    auth.setUsername(userDto.getUsername());
    auth.setFullName(userDto.getFullName());
    auth.setBirthDate(userDto.getBirthDate());

    return ResponseEntity.ok(userService.create(auth));
  }

  @JsonView(DataView.class)
  @PatchMapping("/password")
  public ResponseEntity<UserDto> changePassword (
    @AuthenticationPrincipal User auth,
    @RequestBody PasswordDto passwordDto
  ) {
    if (!(passwordDto.isPasswordEquals() && passwordDto.isNewPasswordValid())) {
      return new ResponseEntity<>(null, null, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if (!passwordEncoder.matches(passwordDto.getOldPassword(), auth.getPassword())) {
      return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
    }

    return ResponseEntity.ok(new UserDto(userService.updatePassword(auth, passwordEncoder.encode(passwordDto.getNewPassword()))));
  }

  @DeleteMapping("/avatar")
  public ResponseEntity<Void> deleteAvatar (
    @AuthenticationPrincipal User auth
  ) {
    userService.deleteAvatar(auth);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete (@PathVariable String id) {
    userService.deleteById(Long.parseLong(id));
    return ResponseEntity.ok().build();
  }
}
