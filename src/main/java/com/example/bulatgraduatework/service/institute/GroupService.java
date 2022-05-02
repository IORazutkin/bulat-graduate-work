package com.example.bulatgraduatework.service.institute;

import com.example.bulatgraduatework.entity.institute.Course;
import com.example.bulatgraduatework.entity.institute.Group;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.institute.GroupRepo;
import com.example.bulatgraduatework.util.CopyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
  private final GroupRepo groupRepo;

  public List<Group> findAllByCourse (Course course) {
    return groupRepo.findAllByCourseAndIsDeletedIsFalseOrderByNum(course);
  }

  public Group findById (Long id) {
    Optional<Group> groupOptional = groupRepo.findById(id);

    if (groupOptional.isPresent()) {
      return groupOptional.get();
    }

    throw new NotFoundException();
  }

  public Group create (Group group) {
    return groupRepo.save(group);
  }

  public Group update (Long id, Group group) {
    Group groupFromDb = findById(id);
    CopyProperties.copy(group, groupFromDb, "id", "course");

    return groupRepo.save(groupFromDb);
  }

  public void delete (Long id) {
    Group group = findById(id);
    group.setIsDeleted(true);
    groupRepo.save(group);
  }
}
