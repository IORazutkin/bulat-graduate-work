package com.example.bulatgraduatework.entity.institute;

import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(DataView.class)
  private Long id;

  @Column
  @JsonView(DataView.class)
  private String title;

  @Column
  @JsonView(DataView.class)
  private String num;

  @Column(columnDefinition = "boolean default false")
  private Boolean isDeleted = false;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "institute_id")
  @JsonView(DataView.class)
  private Institute institute;
}
