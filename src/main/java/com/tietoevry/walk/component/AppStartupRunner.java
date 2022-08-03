package com.tietoevry.walk.component;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.tietoevry.walk.exceptions.ItemNotFoundException;
import com.tietoevry.walk.exceptions.RuleNotFoundException;
import com.tietoevry.walk.form.ItemModel;
import com.tietoevry.walk.form.MeasuringUnitModel;
import com.tietoevry.walk.form.RuleModel;
import com.tietoevry.walk.form.SubjectModel;
import com.tietoevry.walk.service.ItemService;
import com.tietoevry.walk.service.MeasuringUnitService;
import com.tietoevry.walk.service.RuleService;
import com.tietoevry.walk.service.SubjectService;

@Component
public class AppStartupRunner implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppStartupRunner.class);
	
    @Autowired
    private MeasuringUnitService measuringUnitService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RuleService ruleService;

    @Override
	public void run(ApplicationArguments args) throws Exception {
    	MeasuringUnitModel unit = createMeasuringUnitIfNotFound("", "Unit");
    	MeasuringUnitModel meter = createMeasuringUnitIfNotFound("m", "Meter");
    	MeasuringUnitModel kilogram = createMeasuringUnitIfNotFound("kg", "Kilogram");
    	MeasuringUnitModel liter = createMeasuringUnitIfNotFound("l", "Liter");

    	SubjectModel human = createSubjectIfNotFound("human");
    	SubjectModel dog = createSubjectIfNotFound("dog");
		
		RuleModel march = createRuleIfNotFound("march");
		RuleModel people = createSubjectRuleIfNotFound("people", human, null);
		RuleModel person = createSubjectRuleIfNotFound("person", human, 1L);
		RuleModel canine = createSubjectRuleIfNotFound("canine", dog, 1L);
		RuleModel daily = createDailyRuleIfNotFound("daily", dog, 1L);
		RuleModel distance = createDistanceRuleIfNotFound("distance", human, 1L, 12.0);
		RuleModel winter = createWinterRuleIfNotFound("winter", human, 1L);
		RuleModel spring = createSpringRuleIfNotFound("spring", human, 1L);
		RuleModel summer = createSummerRuleIfNotFound("summer", human, 1L);
		RuleModel autumn = createAutumnRuleIfNotFound("autumn", human, 1L);
		RuleModel morning = createMorningRuleIfNotFound("morning", human, 1L, 20.0, true);
		RuleModel afternoon = createAfternoonRuleIfNotFound("afternoon", human, 1L, 25.0, true);
		RuleModel evening = createEveningRuleIfNotFound("evening", human, 1L, 15.0, true);
		RuleModel night = createNightRuleIfNotFound("night", human, 1L, 5.0, true);
		RuleModel sleepover = createNightRuleIfNotFound("sleepover", human, 5L, 4.0, false);

		ItemModel map = createItemIfNotFound("map", unit, Arrays.asList(march), 1);
		ItemModel backpack = createItemIfNotFound("backpack", unit, Arrays.asList(person), 1);
		ItemModel hat = createItemIfNotFound("sun hat", unit, Arrays.asList(summer), 1);
		ItemModel umbrella = createItemIfNotFound("umbrella", unit, Arrays.asList(autumn), 1);
		ItemModel marquee = createItemIfNotFound("marquee", unit, Arrays.asList(sleepover), 1);
		ItemModel spoon = createItemIfNotFound("spoon", unit, Arrays.asList(distance), 1);
		ItemModel switchblade = createItemIfNotFound("switchblade", unit, Arrays.asList(people), 1);
		ItemModel lighter = createItemIfNotFound("lighter", unit, Arrays.asList(people), 2);
		ItemModel fuel = createItemIfNotFound("fuel", liter, Arrays.asList(distance), 1.5);
		ItemModel rope = createItemIfNotFound("rope", meter, Arrays.asList(people), 6);
		ItemModel potatoes = createItemIfNotFound("potatoes", kilogram, Arrays.asList(afternoon), 0.9);
		ItemModel stew = createItemIfNotFound("can of stew", unit, Arrays.asList(afternoon), 1);
		ItemModel porridge = createItemIfNotFound("canned porridge", unit, Arrays.asList(morning), 1);
		ItemModel tea = createItemIfNotFound("packet of tea", unit, Arrays.asList(morning, evening), 1);
		ItemModel leash = createItemIfNotFound("leash", unit, Arrays.asList(canine), 1);
		ItemModel dry = createItemIfNotFound("dry food", unit, Arrays.asList(daily), 2);
	}

	private MeasuringUnitModel createMeasuringUnitIfNotFound(final String name, final String description) {
    	MeasuringUnitModel measuringUnitModel = measuringUnitService.findByName(name);
        if (measuringUnitModel == null) {
        	measuringUnitModel = measuringUnitService.createMeasuringUnit(new MeasuringUnitModel(name, description));
        }
        return measuringUnitModel;
    }
    
    private SubjectModel createSubjectIfNotFound(final String name) {
    	SubjectModel subjectModel = subjectService.findByName(name);
        if (subjectModel == null) {
        	subjectModel = subjectService.createSubject(name);
        }
        return subjectModel;
    }
    
    private ItemModel createItemIfNotFound(final String name, final MeasuringUnitModel measuringUnit, final List<RuleModel> rules, final double quantity) {
    	ItemModel itemModel = itemService.findByName(name);
        if (itemModel == null) {
			itemModel = itemService.createItem(new ItemModel(name, measuringUnit.getName()));
			if (rules != null) {
				for (RuleModel rule: rules) {
					try {
						itemService.addRule(itemModel.getId(), rule.getName(), quantity);
					} catch (ItemNotFoundException e) {
						LOGGER.warn(e.getMessage(), e);
					} catch (RuleNotFoundException e) {
						LOGGER.warn(e.getMessage(), e);
					} 
				}
			}
        }
        return itemModel;
    }
    
    private RuleModel createRuleIfNotFound(final String name) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createRule(name);
        }
        return ruleModel;
    }
    
    private RuleModel createDailyRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createDailyRule(name, subject.getId(), subjectCount);
        }
        return ruleModel;
    }
    
    private RuleModel createSubjectRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createSubjectRule(name, subject.getId(), subjectCount);
        }
        return ruleModel;
    }
    
    private RuleModel createDistanceRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createDistanceRule(name, subject.getId(), subjectCount, distance);
        }
        return ruleModel;
    }
    
    private RuleModel createWinterRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createWinterRule(name, subject.getId(), subjectCount, null, false);
        }
        return ruleModel;
    }
    
    private RuleModel createSpringRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createSpringRule(name, subject.getId(), subjectCount, null, false);
        }
        return ruleModel;
    }
    
    private RuleModel createSummerRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createSummerRule(name, subject.getId(), subjectCount, null, false);
        }
        return ruleModel;
    }
    
    private RuleModel createAutumnRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createAutumnRule(name, subject.getId(), subjectCount, null, false);
        }
        return ruleModel;
    }

    private RuleModel createMorningRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance, final boolean toEvery) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createMorningRule(name, subject.getId(), subjectCount, distance, toEvery);
        }
        return ruleModel;
	}

    private RuleModel createAfternoonRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance, final boolean toEvery) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createAfternoonRule(name, subject.getId(), subjectCount, distance, toEvery);
        }
        return ruleModel;
	}

    private RuleModel createEveningRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance, final boolean toEvery) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createEveningRule(name, subject.getId(), subjectCount, distance, toEvery);
        }
        return ruleModel;
	}

    private RuleModel createNightRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance, final boolean toEvery) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createNightRule(name, subject.getId(), subjectCount, distance, toEvery);
        }
        return ruleModel;
	}
}
