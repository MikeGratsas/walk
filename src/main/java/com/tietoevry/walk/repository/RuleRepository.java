package com.tietoevry.walk.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tietoevry.walk.entity.Rule;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {
	Optional<Rule> findByName(String name); 
}
