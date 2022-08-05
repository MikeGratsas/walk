package com.tietoevry.walk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<ItemModel>> getItemList(final Pageable pageable)
    {
        return ResponseEntity.ok(itemService.listItems(pageable));
    }

    @GetMapping("/items/findByMeasuringUnitName")
    public ResponseEntity<List<ItemModel>> findByMeasuringUnitName(@RequestParam final String name, final Pageable pageable)
    {
        return ResponseEntity.ok(itemService.findByMeasuringUnitName(name, pageable));
    }

    @GetMapping("/items/findByName")
    public ResponseEntity<ItemModel> findItemByName(@RequestParam final String name)
    {
        final ItemModel item = itemService.findByName(name);
		return (item == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @GetMapping(path="/items/{itemId}")
    public ResponseEntity<ItemModel> getItemById(@PathVariable("itemId") final Long itemId)
    {
        ItemModel item;
        try {
            item = itemService.findItem(itemId);
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
    public ResponseEntity<Long> deleteItemById(@PathVariable("itemId") final Long itemId)
    {
		try {
	        itemService.deleteItems(new Long[] { itemId });
		} catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
		return ResponseEntity.ok(itemId);
    }
    
    @GetMapping("/items/{itemId}/rules")
    public ResponseEntity<List<ItemRuleModel>> getItemRuleList(@PathVariable("itemId") final Long itemId)
    {
        try {
			return ResponseEntity.ok(itemService.listRules(itemId));
		}
        catch (ItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
    }

    @GetMapping("/items/{itemId}/rules/add")
    public ResponseEntity<ItemRuleModel> addItemRule(@PathVariable("itemId") final Long itemId, @RequestParam final String rule, @RequestParam final Double quantity)
    {
        try {
			return ResponseEntity.ok(itemService.addRule(itemId, rule, quantity));
		}
        catch (ItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
        catch (RuleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
    }
    
    @DeleteMapping(path="/items/{itemId}/rules/remove")
    public ResponseEntity<Long> removeItemRule(@PathVariable("itemId") final Long itemId, @RequestParam final Long itemRuleId)
    {
        try {
			itemService.removeRule(itemId, itemRuleId);
		} catch (ItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		} catch (ItemRuleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
		}
		return ResponseEntity.ok(itemId);
    }
    
}
