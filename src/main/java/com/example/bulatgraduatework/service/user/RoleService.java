package com.example.bulatgraduatework.service.user;

import com.example.bulatgraduatework.entity.user.Role;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.user.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepo roleRepo;

  public List<Role> findAll () {
    return roleRepo.findAll();
  }

  public Role findById (Long id) {
    Optional<Role> roleOptional = roleRepo.findById(id);

    if (roleOptional.isPresent()) {
      return roleOptional.get();
    }

    throw new NotFoundException();
  }

  public Role getAdmin () {
    return findById(4L);
  }

  public Role getDeanery () {
    return findById(3L);
  }

  public Role getStudent () {
    return findById(1L);
  }

  public Role getTeacher () {
    return findById(2L);
  }
}
