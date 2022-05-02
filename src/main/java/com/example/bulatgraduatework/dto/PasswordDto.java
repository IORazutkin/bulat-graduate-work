package com.example.bulatgraduatework.dto;

import lombok.Data;

@Data
public class PasswordDto {
  private String oldPassword;
  private String newPassword;
  private String confirmNewPassword;

  public boolean isPasswordEquals () {
    return this.newPassword.equals(this.confirmNewPassword);
  }

  public boolean isNewPasswordValid () {
    return !this.oldPassword.equals(this.newPassword);
  }
}
