package com.example.bulatgraduatework.dto;

import com.example.bulatgraduatework.entity.Task;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskDto extends Task {
  @JsonView(DataView.class)
  List<UserDto> students;
  @JsonView(DataView.class)
  UserDto creator;

  public TaskDto () {}

  public TaskDto (Task task) {
    super(task);
    this.students = task.getStudentList().stream().map(UserDto::new).collect(Collectors.toList());
    this.creator = new UserDto(task.getTeacher());
  }
}
