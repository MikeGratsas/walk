package com.tietoevry.walk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tietoevry.walk.form.WalkItemModel;
import com.tietoevry.walk.form.WalkModel;
import com.tietoevry.walk.service.WalkService;

@RestController
@RequestMapping("/api")
public class WalkController {
	
    @Autowired
    private WalkService walkService;

    @PostMapping("/prepare")
	public ResponseEntity<List<WalkItemModel>> prepare(@Valid @RequestBody final WalkModel walk) {
    	final List<WalkItemModel> walkItems = walkService.prepare(walk);
		return ResponseEntity.ok(walkItems); 
	}
}
