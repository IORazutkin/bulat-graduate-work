package com.example.bulatgraduatework.entity.user;

import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class UserPK implements Serializable {
  @OneToOne
  @JoinColumn(name = "user_id")
  @JsonView(DataView.class)
  private User user;

  public UserPK () {}

  public UserPK (User user) {
    this.user = user;
  }
}
