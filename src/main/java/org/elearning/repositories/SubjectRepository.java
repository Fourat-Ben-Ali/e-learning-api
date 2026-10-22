package org.elearning.repositories;

import org.elearning.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,String> {
    Optional<Subject> findByName(String name);
}
