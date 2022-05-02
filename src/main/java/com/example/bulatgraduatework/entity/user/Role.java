package com.example.bulatgraduatework.entity.user;

import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(DataView.class)
  private Long id;

  @Column
  @JsonView(DataView.class)
  private String name;
}
