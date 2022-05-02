package com.example.bulatgraduatework.entity.user;

import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Deanery {
  @EmbeddedId
  @JsonView(DataView.class)
  private UserPK user;

  @ManyToOne
  @JoinColumn(name = "institute_id")
  @JsonView(DataView.class)
  private Institute institute;
}
