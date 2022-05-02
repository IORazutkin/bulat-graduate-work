package com.example.bulatgraduatework.api.user;

import com.example.bulatgraduatework.dto.UserDto;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.exception.UnprocessableEntityException;
import com.example.bulatgraduatework.service.institute.InstituteService;
import com.example.bulatgraduatework.service.user.AuthService;
import com.example.bulatgraduatework.service.user.DeaneryService;
import com.example.bulatgraduatework.service.user.RoleService;
import com.example.bulatgraduatework.service.user.UserService;
import com.example.bulatgraduatework.util.CopyProperties;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deanery")
public class DeaneryController {
  private final DeaneryService deaneryService;
  private final InstituteService instituteService;
  private final AuthService authService;
  private final UserService userService;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  @JsonView(DataView.class)
  @GetMapping("institute/{instituteId}")
  public ResponseEntity<List<UserDto>> findAllByInstitute (@PathVariable String instituteId){
    Institute institute = instituteService.findById(Long.parseLong(instituteId));
    return ResponseEntity.ok(deaneryService.findByInstitute(institute).stream().map(UserDto::new).collect(Collectors.toList()));
  }

  @JsonView(DataView.class)
  @PostMapping
  public ResponseEntity<UserDto> create (@RequestBody UserDto userDto) {
    UserDetails userDetails = authService.loadUserByUsername(userDto.getUsername());

    if (userDetails != null) {
      throw new UnprocessableEntityException();
    }

    Institute institute = instituteService.findById(userDto.getInstitute().getId());
    User user = new User();
    user.setFullName(userDto.getFullName());
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRole(roleService.getDeanery());
    user = userService.create(user);

    return ResponseEntity.ok(new UserDto(deaneryService.create(user, institute)));
  }

  @JsonView(DataView.class)
  @PatchMapping("{id}")
  public ResponseEntity<UserDto> create (@PathVariable String id, @RequestBody UserDto userDto) {
    User user = userService.findByUsername(userDto.getUsername());

    if (user != null && user.getId() != Long.parseLong(id)) {
      throw new UnprocessableEntityException();
    }

    User userFromDb = userService.findById(Long.parseLong(id));
    CopyProperties.copy(userDto, userFromDb, "id", "group", "lessons");
    userFromDb = userService.create(userFromDb);

    return ResponseEntity.ok(new UserDto(deaneryService.findByUser(userFromDb)));
  }
}
