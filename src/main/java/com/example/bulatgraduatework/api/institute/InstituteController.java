package com.example.bulatgraduatework.api.institute;

import com.example.bulatgraduatework.entity.institute.Institute;
import com.example.bulatgraduatework.service.institute.InstituteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institute")
@RequiredArgsConstructor
public class InstituteController {
  private final InstituteService instituteService;

  @GetMapping
  public ResponseEntity<List<Institute>> findAll () {
    return ResponseEntity.ok(instituteService.findAll(Sort.by("title")));
  }

  @GetMapping("{id}")
  public ResponseEntity<Institute> findById (@PathVariable String id) {
    return ResponseEntity.ok(instituteService.findById(Long.parseLong(id)));
  }

  @PostMapping
  public ResponseEntity<Institute> create (@RequestBody Institute institute) {
    return ResponseEntity.ok(instituteService.save(institute));
  }

  @PatchMapping("{id}")
  public ResponseEntity<Institute> update (@PathVariable String id, @RequestBody Institute institute) {
    return ResponseEntity.ok(instituteService.update(Long.parseLong(id), institute));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete (@PathVariable String id) {
    instituteService.deleteById(Long.parseLong(id));
    return ResponseEntity.ok().build();
  }
}
