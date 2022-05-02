package com.example.bulatgraduatework.service.user;

import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.user.UserRepo;
import com.example.bulatgraduatework.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepo userRepo;
  private final FileService fileService;

  public User findById (Long id) {
    Optional<User> userOptional = userRepo.findById(id);

    if (userOptional.isPresent()) {
      return userOptional.get();
    }

    throw new NotFoundException();
  }

  public User findByUsername (String username) {
    return userRepo.findByUsernameAndIsDeletedIsFalse(username);
  }

  public User create (User user) {
    return userRepo.save(user);
  }

  public User updateAvatar (User user, MultipartFile file) throws IOException {
    user.setAvatarPath(fileService.loadFile(file, "avatar"));
    return userRepo.save(user);
  }

  public User updatePassword (User user, String password) {
    user.setPassword(password);
    return userRepo.save(user);
  }

  public User deleteAvatar (User user) {
    fileService.removeFile("avatar", user.getAvatarPath());
    user.setAvatarPath(null);
    return userRepo.save(user);
  }

  public void deleteById (Long id) {
    User user = findById(id);
    user.setIsDeleted(true);
    userRepo.save(user);
  }
}
