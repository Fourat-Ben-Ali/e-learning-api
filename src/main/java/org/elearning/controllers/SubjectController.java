package org.elearning.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elearning.requests.SubjectRequest;
import org.elearning.responses.SubjectResponse;
import org.elearning.services.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Void> createSubject(
        @Valid
        @RequestBody
        final SubjectRequest request
    ){
        this.subjectService.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<Void> updateCtegory(
            @Valid
            @RequestBody
            final SubjectRequest request,
            @PathVariable("subjectId")
        final String id
    ){
        this.subjectService.update(id ,request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubjectById(
            @PathVariable("subjectId")
            final String id
    ){
         this.subjectService.deleteById(id);
         return ResponseEntity.noContent().build();
    }
    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectResponse> getSubjectById(
       @PathVariable("subjectId")
       final String id
    ){
        return ResponseEntity.ok(this.subjectService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAllSubjects(){
        return ResponseEntity.ok(this.subjectService.findAll());
    }

}
