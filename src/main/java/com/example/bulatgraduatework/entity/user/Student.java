package com.example.bulatgraduatework.entity.user;

import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Student {
  @EmbeddedId
  @JsonView(DataView.class)
  private UserPK user;

  @ManyToOne
  @JsonView(DataView.class)
  private Group group;
}
