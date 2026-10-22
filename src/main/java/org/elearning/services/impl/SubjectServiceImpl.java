package org.elearning.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elearning.entities.Subject;
import org.elearning.mappers.SubjectMapper;
import org.elearning.repositories.SubjectRepository;
import org.elearning.requests.SubjectRequest;
import org.elearning.responses.SubjectResponse;
import org.elearning.services.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public void create(SubjectRequest request) {
     //check if Subject already exists by name
        checkIfSubjectExistsByName(request.getName());

        final Subject subject =subjectMapper.toEntity(request);
        this.subjectRepository.save(subject);
    }


    @Override
    public void update(String id, SubjectRequest request) {
        final Optional <Subject> existingSubject = this.subjectRepository.findById(id);

        if(existingSubject.isEmpty()){
            log.debug("Subject not found");
            throw new EntityNotFoundException("Subject not found");
        }


        final Subject subject = existingSubject.get();

        if(!subject.getName().equalsIgnoreCase(request.getName()) ){
            checkIfSubjectExistsByName(request.getName());
        }

        final Subject updatedSubject = subjectMapper.toEntity(request);
        updatedSubject.setId(id);
        this.subjectRepository.save(updatedSubject);

    }

    @Override
    public SubjectResponse findById(String id) {

        return this.subjectRepository.findById(id).map(this.subjectMapper::toResponse)
                .orElseThrow(() ->  new EntityNotFoundException("Subject not found"));
    }

    @Override
    public void deleteById(final String id) {

        final Subject subject = this.subjectRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        this.subjectRepository.delete(subject);
    }

    @Override
    public List<SubjectResponse> findAll() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::toResponse)
                .toList();
    }
    private void checkIfSubjectExistsByName(String name) {
        final Optional<Subject> subject = this.subjectRepository.findByName(name);

        if (subject.isPresent()){
            log.debug("Subject already exists");
            throw new RuntimeException("Subject already exists");
        }
    }
}
