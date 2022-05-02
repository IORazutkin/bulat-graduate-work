package com.example.bulatgraduatework.service.institute;

import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.institute.InstituteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstituteService {
  private final InstituteRepo instituteRepo;

  public List<Institute> findAll () {
    return instituteRepo.findAllByIsDeletedIsFalse();
  }

  public List<Institute> findAll (Sort sort) {
    return instituteRepo.findAllByIsDeletedIsFalse(sort);
  }

  public Institute findById (Long id) {
    Optional<Institute> instituteOptional = instituteRepo.findById(id);

    if (instituteOptional.isPresent()) {
      return instituteOptional.get();
    }

    throw new NotFoundException();
  }

  public Institute save (Institute institute) {
    return instituteRepo.save(institute);
  }

  public Institute update (Long id, Institute institute) {
    Institute instituteFromDb = findById(id);
    instituteFromDb.setTitle(institute.getTitle());

    return instituteRepo.save(instituteFromDb);
  }

  public void deleteById (Long id) {
    instituteRepo.delete(findById(id));
  }
}
