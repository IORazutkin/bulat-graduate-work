package com.example.bulatgraduatework.service;

import com.example.bulatgraduatework.dto.DocumentDto;
import com.example.bulatgraduatework.entity.Document;
import com.example.bulatgraduatework.entity.Task;
import com.example.bulatgraduatework.entity.user.Deanery;
import com.example.bulatgraduatework.entity.user.User;
import com.example.bulatgraduatework.exception.NotFoundException;
import com.example.bulatgraduatework.repo.DocumentRepo;
import com.example.bulatgraduatework.util.CopyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
  private final DocumentRepo documentRepo;
  private final FileService fileService;

  public Document findById (Long id) {
    Optional<Document> documentOptional = documentRepo.findById(id);

    if (documentOptional.isPresent()) {
      return documentOptional.get();
    }

    throw new NotFoundException();
  }

  public List<Document> findDeaneryDocs () {
    List<Document> documents = documentRepo.findAllByIsDeletedIsFalseAndVerifiedStatusIsNull();
    documents.addAll(documentRepo.findAllByIsDeletedIsFalseAndTaskIsNull());
    return documents;
  }

  public List<Document> findUserDocs () {
    return documentRepo.findAllByIsDeletedIsFalseAndTaskIsNull();
  }

  public List<Document> findByTask (Task task) {
    return documentRepo.findAllByTaskAndIsDeletedIsFalse(task);
  }

  public List<Document> findByTaskAndIsVerified (Task task) {
    return documentRepo.findAllByTaskAndIsDeletedIsFalse(task).stream()
      .map(this::getVerified)
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
  }

  public Document verify (Long id, User deanery, Boolean status) {
    Document document = findById(id);
    document.setVerifiedStatus(status);
    document.setVerifiedBy(deanery);
    return documentRepo.save(document);
  }

  public Document create (DocumentDto documentDto, Task task) throws IOException {
    String path = fileService.loadFile(documentDto.getFile(), "document");
    Document document = new Document();
    document.setFilePath(path);
    document.setTitle(documentDto.getTitle());
    document.setTask(task);

    return documentRepo.save(document);
  }

  public Document create (DocumentDto documentDto, User user) throws IOException {
    String path = fileService.loadFile(documentDto.getFile(), "document");
    Document document = new Document();
    document.setFilePath(path);
    document.setTitle(documentDto.getTitle());
    document.setVerifiedStatus(true);
    document.setVerifiedBy(user);

    return documentRepo.save(document);
  }

  public Document update (Long id, MultipartFile file) throws IOException {
    Document documentFromDb = findById(id);
    Document document = new Document();
    document.setTitle(documentFromDb.getTitle());
    document.setTask(documentFromDb.getTask());
    document.setPrevVersion(documentFromDb);
    document.setFilePath(fileService.loadFile(file, "document"));
    documentFromDb.setIsDeleted(true);
    documentRepo.save(documentFromDb);
    return documentRepo.save(document);
  }

  public Document updateFull (Long id, DocumentDto documentDto, User user) throws IOException {
    Document documentFromDb = findById(id);
    documentFromDb.setTitle(documentDto.getTitle());

    if (documentDto.getFile() != null) {
      documentFromDb.setFilePath(fileService.loadFile(documentDto.getFile(), "document"));
    }

    documentFromDb.setVerifiedBy(user);
    return documentRepo.save(documentFromDb);
  }

  public Document updateTitle (Long id, String title) {
    Document document = findById(id);
    document.setTitle(title);
    return documentRepo.save(document);
  }

  public Document actualById (Long currentId, Long newId) {
    Document current = findById(currentId);
    Document document = findById(newId);
    Document newDocument = new Document();
    CopyProperties.copy(document, newDocument, "id", "isDeleted", "createdAt");
    newDocument.setPrevVersion(current);
    newDocument.setIsDeleted(false);
    current.setIsDeleted(true);
    documentRepo.save(current);
    return documentRepo.save(newDocument);
  }

  public void deleteById (Long id) {
    Document document = findById(id);
    document.setIsDeleted(true);
    documentRepo.save(document);
  }

  private Document getVerified (Document document) {
    if (document.getVerifiedStatus() != null && document.getVerifiedStatus()) {
      return document;
    }

    if (document.getPrevVersion() == null) {
      return null;
    }

    return getVerified(document.getPrevVersion());
  }
}
