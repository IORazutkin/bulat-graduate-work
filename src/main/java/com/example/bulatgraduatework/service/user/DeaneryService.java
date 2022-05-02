package com.example.bulatgraduatework.service.user;

import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.Deanery;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.entity.user.UserPK;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.user.DeaneryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeaneryService {
  private final DeaneryRepo deaneryRepo;

  public Deanery findByUser (User user) {
    Optional<Deanery> teacherOptional = deaneryRepo.findById(new UserPK(user));

    if (teacherOptional.isPresent()) {
      return teacherOptional.get();
    }

    throw new NotFoundException();
  }

  public List<Deanery> findByInstitute (Institute institute) {
    return deaneryRepo.findAllByInstituteAndUser_User_IsDeletedIsFalse(institute);
  }

  public Deanery create (User user, Institute institute) {
    Deanery deanery = new Deanery();
    deanery.setUser(new UserPK(user));
    deanery.setInstitute(institute);
    return deaneryRepo.save(deanery);
  }
}
