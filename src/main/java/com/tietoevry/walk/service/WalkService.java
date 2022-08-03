package com.tietoevry.walk.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tietoevry.walk.entity.Item;
import com.tietoevry.walk.entity.MeasuringUnit;
import com.tietoevry.walk.entity.Rule;
import com.tietoevry.walk.entity.RuleItem;
import com.tietoevry.walk.form.ItemModel;
import com.tietoevry.walk.form.MeasuringUnitModel;
import com.tietoevry.walk.form.ItemQuantityModel;
import com.tietoevry.walk.form.WalkItemModel;
import com.tietoevry.walk.form.WalkModel;
import com.tietoevry.walk.repository.RuleRepository;

@Service
public class WalkService {

    @Autowired
    private RuleRepository ruleRepository;

    public List<WalkItemModel> prepare(final WalkModel walk) {
		List<WalkItemModel> walkItems = StreamSupport.stream(ruleRepository.findAll().spliterator(), false).flatMap(r -> processRule(r, walk).stream()).collect(Collectors.groupingBy(ItemQuantityModel::getItem, Collectors.summingDouble(ItemQuantityModel::getQuantity))).entrySet().stream().map(WalkService::assembleWalkItemModel).sorted(Comparator.comparingLong(WalkItemModel::getId)).collect(Collectors.toList());
		return walkItems; 
	}
    
    private static List<ItemQuantityModel> processRule(final Rule ruleEntity, final WalkModel walk) {
    	long count = ruleEntity.check(walk);
    	return (count > 0)? ruleEntity.getItems().stream().map(ri -> assembleItemQuantityModel(ri, count)).collect(Collectors.toList()): Collections.emptyList();
    }
    
    private static ItemQuantityModel assembleItemQuantityModel(final RuleItem ruleItemEntity, final long count) {
    	return new ItemQuantityModel(ruleItemEntity.getId(), assembleItemModel(ruleItemEntity.getItem()), ruleItemEntity.getQuantity() * count);
    }
    
    private static ItemModel assembleItemModel(final Item itemEntity) {
        MeasuringUnitModel measuringUnitModel = null;
        MeasuringUnit m = itemEntity.getMeasuringUnit();
        if (m != null) {
            measuringUnitModel = new MeasuringUnitModel(m.getId(), m.getName(), m.getDescription());
        }
    	return new ItemModel(itemEntity.getId(), itemEntity.getName(), measuringUnitModel.getName());
    }
    
    private static WalkItemModel assembleWalkItemModel(final Map.Entry<ItemModel, Double> entry) {
    	final ItemModel item = entry.getKey();
    	final WalkItemModel walkItem = new WalkItemModel(item.getId(), item.getName(), item.getMeasuringUnit());
    	walkItem.setQuantity(entry.getValue());
    	return walkItem;
    }
}
