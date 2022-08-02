package com.tietoevry.walk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.tietoevry.walk.exceptions.ItemNotFoundException;
import com.tietoevry.walk.exceptions.ItemRuleNotFoundException;
import com.tietoevry.walk.exceptions.ItemUpdatedException;
import com.tietoevry.walk.exceptions.RuleNotFoundException;
import com.tietoevry.walk.form.ItemModel;
import com.tietoevry.walk.form.ItemRuleModel;
import com.tietoevry.walk.service.ItemService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<ItemModel>> getItemList()
    {
        return ResponseEntity.ok(itemService.listItems());
    }

    @GetMapping("/items/findByMeasuringUnitName")
    public ResponseEntity<List<ItemModel>> findByMeasuringUnitName(@RequestParam final String name)
    {
        return ResponseEntity.ok(itemService.findByMeasuringUnitName(name));
    }

    @GetMapping("/items/findByName")
    public ResponseEntity<ItemModel> findItemByName(@RequestParam final String name)
    {
        ItemModel item = itemService.findByName(name);
        if (item == null)
        	return ResponseEntity.notFound().build();
        return ResponseEntity.ok(item);
    }

    @GetMapping(path="/items/{itemId}")
    public ResponseEntity<ItemModel> getItemById(@PathVariable("itemId") final Long id)
    {
        ItemModel item;
        try {
            item = itemService.findItem(id);
        }
        catch (ItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping("/items")
    public ResponseEntity<ItemModel> createItem(@Valid @RequestBody final ItemModel item)
    {
        final ItemModel itemModel = itemService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemModel);
    }

    @PutMapping(path="/items")
    public ResponseEntity<ItemModel> updateItem(@Valid @RequestBody final ItemModel item)
    {
        try {
            final ItemModel itemModel = itemService.saveItem(item);
            return ResponseEntity.ok(itemModel);
        }
        catch (ItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
        }
        catch (ItemUpdatedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage(), e);
        }
    }

    @DeleteMapping(path="/items/{itemId}")
    public ResponseEntity<Long> deleteItemById(@PathVariable("itemId") final Long id)
    {
		try {
	        itemService.deleteItems(new Long[] { id });
		} catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
		return ResponseEntity.ok(id);
    }
    
    @GetMapping("/items/{itemId}/rules")
    public ResponseEntity<List<ItemRuleModel>> getItemRuleList(@PathVariable("itemId") final Long id)
    {
        try {
			return ResponseEntity.ok(itemService.listRules(id));
		}
        catch (ItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
    }

    @GetMapping("/items/{itemId}/rules/add")
    public ResponseEntity<ItemRuleModel> addItemRule(@PathVariable("itemId") final Long id, @RequestParam final String rule, @RequestParam final Double quantity)
    {
        try {
			return ResponseEntity.ok(itemService.addRule(id, rule, quantity));
		}
        catch (ItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
        catch (RuleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
    }
    
    @DeleteMapping(path="/items/{itemId}/rules/remove")
    public ResponseEntity<Long> removeItemRule(@PathVariable("itemId") final Long id, @RequestParam final Long itemRuleId)
    {
        try {
			itemService.removeRule(id, itemRuleId);
		} catch (ItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		} catch (ItemRuleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
		return ResponseEntity.ok(id);
    }
    
}
