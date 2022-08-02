package com.tietoevry.walk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tietoevry.walk.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
	Optional<Item> findByName(String name); 
	List<Item> findByMeasuringUnitName(String name); 
}
