package com.example.bulatgraduatework.service.user;

import com.example.bulatgraduatework.repo.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
  private final UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
    return userRepo.findByUsernameAndIsDeletedIsFalse(username);
  }
}
