package com.example.bulatgraduatework.entity.user;

import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Teacher {
  @EmbeddedId
  @JsonView(DataView.class)
  private UserPK user;

  @ManyToMany
  @JoinColumn(name = "lesson_list")
  @JsonView(DataView.class)
  private List<Lesson> lessons = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "institute_id")
  @JsonView(DataView.class)
  private Institute institute;
}
