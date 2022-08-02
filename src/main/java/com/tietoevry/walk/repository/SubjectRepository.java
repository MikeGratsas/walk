package com.tietoevry.walk.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tietoevry.walk.entity.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {
	Optional<Subject> findByName(String name); 
}
