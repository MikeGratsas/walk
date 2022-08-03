package com.tietoevry.walk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tietoevry.walk.entity.Item;
import com.tietoevry.walk.entity.RuleItem;
import com.tietoevry.walk.entity.MeasuringUnit;
import com.tietoevry.walk.entity.Rule;
import com.tietoevry.walk.exceptions.ItemNotFoundException;
import com.tietoevry.walk.exceptions.ItemUpdatedException;
import com.tietoevry.walk.exceptions.ItemRuleNotFoundException;
import com.tietoevry.walk.exceptions.RuleNotFoundException;
import com.tietoevry.walk.form.ItemModel;
import com.tietoevry.walk.form.ItemRuleModel;
import com.tietoevry.walk.form.MeasuringUnitModel;
import com.tietoevry.walk.repository.ItemRepository;
import com.tietoevry.walk.repository.MeasuringUnitRepository;
import com.tietoevry.walk.repository.RuleRepository;

@Service
public class ItemService {

    @Autowired
    private MeasuringUnitRepository measuringUnitRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemModel> listItems(final Pageable pageable) {
    	final Page<Item> itemList = itemRepository.findAll(pageable);
        return itemList.stream().map(ItemService::assembleItemModel).collect(Collectors.toList());
    }

    public ItemModel createItem(ItemModel itemModel) {
    	if (itemModel == null)
    		throw new IllegalArgumentException("itemModel");
        final Item itemEntity = new Item(itemModel.getName());
        final Optional<MeasuringUnit> measuringUnitOptional = measuringUnitRepository.findByName(itemModel.getMeasuringUnit());
        if (measuringUnitOptional.isPresent()) {
        	final MeasuringUnit measuringUnitEntity = measuringUnitOptional.get();
            itemEntity.setMeasuringUnit(measuringUnitEntity);
        }
        final Item item = itemRepository.save(itemEntity);
        return assembleItemModel(item);
    }

    public ItemModel saveItem(ItemModel itemModel) throws ItemNotFoundException, ItemUpdatedException {
    	if (itemModel == null)
    		throw new IllegalArgumentException("itemModel");
        Item itemEntity;
        Long id = itemModel.getId();
        if (id != null) {
            Optional<Item> itemOptional = itemRepository.findById(id);
            if (itemOptional.isPresent()) {
                itemEntity = itemOptional.get();
                if (!itemEntity.getLastUpdated().equals(itemModel.getLastUpdated())) {
                    throw new ItemUpdatedException(id);
                }
            }
            else {
                throw new ItemNotFoundException(id);
            }
        }
        else {
            itemEntity = new Item();
        }
        itemEntity.setName(itemModel.getName());
        final Optional<MeasuringUnit> measuringUnitOptional = measuringUnitRepository.findByName(itemModel.getMeasuringUnit());
        if (measuringUnitOptional.isPresent()) {
        	final MeasuringUnit measuringUnitEntity = measuringUnitOptional.get();
            itemEntity.setMeasuringUnit(measuringUnitEntity);
        }
        final Item item = itemRepository.save(itemEntity);
        return assembleItemModel(item);
    }

    public ItemModel findItem(final Long id) throws ItemNotFoundException {
        ItemModel itemModel = null;
        final Optional<Item> itemEntity = itemRepository.findById(id);
        if (itemEntity.isPresent()) {
            itemModel = assembleItemModel(itemEntity.get());
        }
        else {
            throw new ItemNotFoundException(id);
        }
        return itemModel;
    }

    public ItemModel findByName(final String name) {
        ItemModel itemModel = null;
        final Optional<Item> itemEntity = itemRepository.findByName(name);
        if (itemEntity.isPresent()) {
            itemModel = assembleItemModel(itemEntity.get());
        }
        return itemModel;
    }

    public void deleteItems(Long[] ids) {
        for (Long id: ids) {
            itemRepository.deleteById(id);
        }
    }

    public List<ItemModel> findByMeasuringUnitName(final String name, final Pageable pageable) {
    	final List<Item> itemList = (List<Item>)itemRepository.findByMeasuringUnitName(name, pageable);
        return itemList.stream().map(ItemService::assembleItemModel).collect(Collectors.toList());
    }

    @Transactional
    public List<ItemRuleModel> listRules(Long id) throws ItemNotFoundException {
    	final Optional<Item> itemEntity = itemRepository.findById(id);
        if (!itemEntity.isPresent()) {
            throw new ItemNotFoundException(id);
        }
    	final Item item = itemEntity.get();
    	final List<RuleItem> itemList = item.getItemRules();
        return itemList.stream().map(ItemService::assembleItemRuleModel).collect(Collectors.toList());
    }

    @Transactional
    public ItemRuleModel addRule(Long id, String ruleName, Double quantity) throws ItemNotFoundException, RuleNotFoundException {
    	final Optional<Item> itemOptional = itemRepository.findById(id);
        if (!itemOptional.isPresent()) {
            throw new ItemNotFoundException(id);
        }
    	final Item itemEntity = itemOptional.get();
    	final Optional<Rule> ruleOptional = ruleRepository.findByName(ruleName);
        if (!ruleOptional.isPresent()) {
            throw new RuleNotFoundException(ruleName);
        }
        final Rule ruleEntity = ruleOptional.get();                
        final RuleItem ruleItem = new RuleItem(ruleEntity, quantity);
        itemEntity.addRule(ruleItem);
        Item item = itemRepository.save(itemEntity);
        final Optional<RuleItem> ruleItemOptional = item.getItemRules().stream().filter(ir -> ruleEntity.equals(ir.getRule())).findFirst();
        return ruleItemOptional.isPresent()? assembleItemRuleModel(ruleItemOptional.get()): null;
    }

    @Transactional
    public void removeRule(Long id, Long itemRuleId) throws ItemNotFoundException, ItemRuleNotFoundException {
    	final Optional<Item> itemEntity = itemRepository.findById(id);
        if (!itemEntity.isPresent()) {
            throw new ItemNotFoundException(id);
        }
    	final Item item = itemEntity.get();
    	final List<RuleItem> itemRules = item.getItemRules();
        if (!itemRules.removeIf(ir -> ir.getId() == itemRuleId)) {
            throw new ItemRuleNotFoundException(itemRuleId);
        }
        itemRepository.save(item);
    }

    private static ItemRuleModel assembleItemRuleModel(final RuleItem ruleItemEntity) {
    	return new ItemRuleModel(ruleItemEntity.getId(), ruleItemEntity.getRule().getName(), ruleItemEntity.getQuantity(), ruleItemEntity.getCreated(), ruleItemEntity.getLastUpdated());
    }
    
    private static ItemModel assembleItemModel(Item itemEntity) {
        MeasuringUnitModel measuringUnitModel = null;
        MeasuringUnit m = itemEntity.getMeasuringUnit();
        if (m != null) {
            measuringUnitModel = new MeasuringUnitModel(m.getId(), m.getName(), m.getDescription());
        }
        return new ItemModel(itemEntity.getId(), itemEntity.getName(), measuringUnitModel.getName(), itemEntity.getCreated(), itemEntity.getLastUpdated());
    }
}
