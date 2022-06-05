package com.example.bulatgraduatework.api;

import com.example.bulatgraduatework.dto.DocumentDto;
import com.example.bulatgraduatework.entity.Document;
import com.example.bulatgraduatework.entity.Task;
import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.service.DocumentService;
import com.example.bulatgraduatework.service.TaskService;
import com.example.bulatgraduatework.service.institute.InstituteService;
import com.example.bulatgraduatework.view.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {
  private final DocumentService documentService;
  private final TaskService taskService;
  private final InstituteService instituteService;

  @JsonView(DataView.class)
  @GetMapping("actual")
  public ResponseEntity<Document> actualById (
    @RequestParam String currentId,
    @RequestParam String newId
  ) {
    return ResponseEntity.ok(documentService.actualById(Long.parseLong(currentId), Long.parseLong(newId)));
  }

  @JsonView(DataView.class)
  @GetMapping("deanery")
  public ResponseEntity<List<Document>> findDeanery () {
    return ResponseEntity.ok(documentService.findDeaneryDocs());
  }

  @JsonView(DataView.class)
  @GetMapping("deanery/stats/{instituteId}")
  public ResponseEntity<Map<String, Integer>> getDocumentsStat (@PathVariable String instituteId) {
    Institute institute = instituteService.findById(Long.parseLong(instituteId));

    return ResponseEntity.ok(documentService.getDocumentStatsByInstitute(institute));
  }

  @JsonView(DataView.class)
  @GetMapping("task/{taskId}")
  public ResponseEntity<List<Document>> findByTask (@PathVariable String taskId) {
    Task task = taskService.findById(Long.parseLong(taskId));
    return ResponseEntity.ok(documentService.findByTask(task));
  }

  @JsonView(DataView.class)
  @GetMapping("student")
  public ResponseEntity<List<Document>> findByTaskAndVerified () {
    return ResponseEntity.ok(documentService.findUserDocs());
  }

  @JsonView(DataView.class)
  @GetMapping("task/student/{taskId}")
  public ResponseEntity<List<Document>> findByTaskAndVerified (@PathVariable String taskId) {
    Task task = taskService.findById(Long.parseLong(taskId));
    return ResponseEntity.ok(documentService.findByTaskAndIsVerified(task));
  }

  @JsonView(DataView.class)
  @GetMapping("verify/{id}")
  public ResponseEntity<Document> findByTask (
    @PathVariable String id,
    @RequestParam Boolean status,
    @AuthenticationPrincipal User user
  ) {
    return ResponseEntity.ok(documentService.verify(Long.parseLong(id), user, status));
  }

  @JsonView(DataView.class)
  @PostMapping("task/{taskId}")
  public ResponseEntity<Document> create (
    @PathVariable String taskId,
    @ModelAttribute DocumentDto documentDto
  ) throws IOException {
    Task task = taskService.findById(Long.parseLong(taskId));
    return ResponseEntity.ok(documentService.create(documentDto, task));
  }

  @JsonView(DataView.class)
  @PostMapping("deanery")
  public ResponseEntity<Document> create (
    @ModelAttribute DocumentDto documentDto,
    @AuthenticationPrincipal User auth
  ) throws IOException {
    return ResponseEntity.ok(documentService.create(documentDto, auth));
  }

  @JsonView(DataView.class)
  @PostMapping("{id}")
  public ResponseEntity<Document> update (
    @PathVariable String id,
    @RequestBody MultipartFile file
  ) throws IOException {
    return ResponseEntity.ok(documentService.update(Long.parseLong(id), file));
  }

  @JsonView(DataView.class)
  @PatchMapping("{id}")
  public ResponseEntity<Document> updateTitle (
    @PathVariable String id,
    @RequestBody DocumentDto documentDto
  ) {
    return ResponseEntity.ok(documentService.updateTitle(Long.parseLong(id), documentDto.getTitle()));
  }

  @JsonView(DataView.class)
  @PatchMapping("deanery/{id}")
  public ResponseEntity<Document> updateFull (
    @PathVariable String id,
    @ModelAttribute DocumentDto documentDto,
    @AuthenticationPrincipal User auth
  ) throws IOException {
    return ResponseEntity.ok(documentService.updateFull(Long.parseLong(id), documentDto, auth));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete (@PathVariable String id) {
    documentService.deleteById(Long.parseLong(id));
    return ResponseEntity.ok().build();
  }
}
