package com.example.bulatgraduatework.repo.user;

import com.example.bulatgraduatework.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
  User findByUsernameAndIsDeletedIsFalse(String username);
}
