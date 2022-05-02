package com.example.bulatgraduatework.entity.user;

import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Embeddable
@Entity(name = "usr")
public class User implements Serializable, UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(DataView.class)
  private Long id;

  @Column
  @JsonView(DataView.class)
  private String fullName;

  @Column
  @JsonView(DataView.class)
  private LocalDate birthDate;

  @Column
  @JsonView(DataView.class)
  private String avatarPath;

  @Column
  @JsonView(DataView.class)
  private String username;

  @Column
  private String password;

  @Column(columnDefinition = "boolean default false")
  private Boolean isDeleted = false;

  @ManyToOne
  @JoinColumn(name = "role_id")
  @JsonView(DataView.class)
  private Role role;

  public User (User user) {
    this.id = user.getId();
    this.fullName = user.getFullName();
    this.birthDate = user.getBirthDate();
    this.avatarPath = user.getAvatarPath();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.role = user.getRole();
    this.isDeleted = false;
  }

  public User () {}

  public Boolean isStudent () {
    return this.getRole().getName().equals("STUDENT");
  }

  @Override
  public boolean isAccountNonExpired () {
    return true;
  }

  @Override
  public boolean isAccountNonLocked () {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired () {
    return true;
  }

  @Override
  public boolean isEnabled () {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities () {
    return null;
  }
}
