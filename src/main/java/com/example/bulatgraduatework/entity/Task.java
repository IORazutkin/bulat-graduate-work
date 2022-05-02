package com.example.bulatgraduatework.entity;

import com.example.bulatgraduatework.entity.user.Student;
import com.example.bulatgraduatework.entity.user.Teacher;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(DataView.class)
  private Long id;

  @Column
  @JsonView(DataView.class)
  private String title;

  @ManyToMany
  @JoinColumn(name = "student_list")
  private List<Student> studentList;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "lesson_id")
  @JsonView(DataView.class)
  private Lesson lesson;

  @Column(columnDefinition = "boolean default false")
  private Boolean isDeleted = false;

  public Task () {}

  public Task (Task task) {
    this.id = task.id;
    this.title = task.title;
    this.isDeleted = false;
    this.lesson = task.lesson;
    this.teacher = task.teacher;
    this.studentList = task.studentList;
  }
}
