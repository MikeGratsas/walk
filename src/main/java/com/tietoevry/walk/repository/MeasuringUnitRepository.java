package com.tietoevry.walk.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tietoevry.walk.entity.MeasuringUnit;

@Repository
public interface MeasuringUnitRepository extends CrudRepository<MeasuringUnit, Long> {
	Optional<MeasuringUnit> findByName(String name); 
}
