package com.example.bulatgraduatework.entity;

import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(DataView.class)
  private Long id;

  @Column
  @JsonView(DataView.class)
  private String title;

  @Column
  @JsonView(DataView.class)
  private String filePath;

  @Column
  @JsonView(DataView.class)
  private LocalDate createdAt = LocalDate.now();

  @OneToOne
  @JoinColumn(name = "prev_version")
  @JsonView(DataView.class)
  private Document prevVersion;

  @Column
  @JsonView(DataView.class)
  private Boolean verifiedStatus = null;

  @ManyToOne
  @JoinColumn(name = "verified_by")
  @JsonView(DataView.class)
  private User verifiedBy;

  @ManyToOne
  @JoinColumn(name = "task_id")
  @JsonView(DataView.class)
  private Task task;

  @Column(columnDefinition = "boolean default false")
  private Boolean isDeleted = false;
}
