package com.example.bulatgraduatework.service;

import com.example.bulatgraduatework.dto.TaskDto;
import com.example.bulatgraduatework.dto.UserDto;
import com.example.bulatgraduatework.entity.Lesson;
import com.example.bulatgraduatework.entity.Task;
import com.example.bulatgraduatework.entity.user.Student;
import com.example.bulatgraduatework.entity.user.Teacher;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepo taskRepo;

  public Task findById (Long id) {
    Optional<Task> taskOptional = taskRepo.findById(id);

    if (taskOptional.isPresent()) {
      return taskOptional.get();
    }

    throw new NotFoundException();
  }

  public List<Task> findByLesson (Lesson lesson) {
    return taskRepo.findAllByLessonAndIsDeletedIsFalse(lesson);
  }

  public List<Task> findByStudent (Student student) {
    return taskRepo.findAllByStudentListContainsAndIsDeletedIsFalse(student);
  }

  public Task create (TaskDto taskDto, Lesson lesson, Teacher teacher, List<Student> studentList) {
    Task task = new Task(taskDto);
    task.setLesson(lesson);
    task.setStudentList(studentList);
    task.setTeacher(teacher);
    return taskRepo.save(task);
  }

  public Task update (Long id, TaskDto taskDto, List<Student> studentList) {
    Task task = findById(id);
    task.setTitle(taskDto.getTitle());
    task.setStudentList(studentList);
    return taskRepo.save(task);
  }

  public void deleteById (Long id) {
    Task task = findById(id);
    task.setIsDeleted(true);
    taskRepo.save(task);
  }
}
