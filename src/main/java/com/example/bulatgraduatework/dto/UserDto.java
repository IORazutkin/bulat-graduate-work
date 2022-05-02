package com.example.bulatgraduatework.dto;

import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.Deanery;
import com.example.bulatgraduatework.entity.user.Student;
import com.example.bulatgraduatework.entity.user.Teacher;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends User {
  @JsonView(DataView.class)
  private Group group;
  @JsonView(DataView.class)
  private List<Lesson> lessons;
  @JsonView(DataView.class)
  private Institute institute;

  public UserDto () {}

  public UserDto (Student student) {
    super(student.getUser().getUser());
    this.group = student.getGroup();
  }

  public UserDto (Teacher teacher) {
    super(teacher.getUser().getUser());
    this.lessons = teacher.getLessons();
    this.institute = teacher.getInstitute();
  }

  public UserDto (Deanery deanery) {
    super(deanery.getUser().getUser());
    this.institute = deanery.getInstitute();
  }

  public UserDto (User user) {
    super(user);
  }
}
