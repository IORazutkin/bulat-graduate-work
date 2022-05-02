package com.example.bulatgraduatework.entity;

import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Lesson {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(DataView.class)
  private Long id;

  @Column
  @JsonView(DataView.class)
  private String title;

  @ManyToOne
  @JoinColumn(name = "course_id")
  @JsonView(DataView.class)
  private Course course;

  @Column(columnDefinition = "boolean default false")
  private Boolean isDeleted = false;
}
