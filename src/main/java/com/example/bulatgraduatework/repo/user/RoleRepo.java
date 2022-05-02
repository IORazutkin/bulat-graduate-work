package com.example.bulatgraduatework.repo.user;

import com.example.bulatgraduatework.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo  extends JpaRepository<Role, Long> {
}
