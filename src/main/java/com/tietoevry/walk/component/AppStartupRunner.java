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
    	final MeasuringUnitModel unit = createMeasuringUnitIfNotFound("", "Unit");
    	final MeasuringUnitModel meter = createMeasuringUnitIfNotFound("m", "Meter");
    	final MeasuringUnitModel kilogram = createMeasuringUnitIfNotFound("kg", "Kilogram");
    	final MeasuringUnitModel liter = createMeasuringUnitIfNotFound("l", "Liter");

    	final SubjectModel human = createSubjectIfNotFound("human");
    	final SubjectModel dog = createSubjectIfNotFound("dog");
		
    	final RuleModel march = createRuleIfNotFound("march");
    	final RuleModel people = createSubjectRuleIfNotFound("people", human, null);
    	final RuleModel person = createSubjectRuleIfNotFound("person", human, 1L);
		final RuleModel canine = createSubjectRuleIfNotFound("canine", dog, 1L);
		final RuleModel daily = createDailyRuleIfNotFound("daily", dog, 1L);
		final RuleModel distance = createDistanceRuleIfNotFound("distance", human, 1L, 12.0);
		final RuleModel winter = createWinterRuleIfNotFound("winter", human, 1L);
		final RuleModel spring = createSpringRuleIfNotFound("spring", human, 1L);
		final RuleModel summer = createSummerRuleIfNotFound("summer", human, 1L);
		final RuleModel autumn = createAutumnRuleIfNotFound("autumn", human, 1L);
		final RuleModel morning = createMorningRuleIfNotFound("morning", human, 1L, 20.0, true);
		final RuleModel afternoon = createAfternoonRuleIfNotFound("afternoon", human, 1L, 25.0, true);
		final RuleModel evening = createEveningRuleIfNotFound("evening", human, 1L, 15.0, true);
		final RuleModel night = createNightRuleIfNotFound("night", human, 1L, 5.0, true);
		final RuleModel sleepover = createNightRuleIfNotFound("sleepover", human, 5L, 4.0, false);
		final RuleModel breakfast = createMorningRuleIfNotFound("breakfast", human, 1L, 20.0, false);
		final RuleModel lunch = createAfternoonRuleIfNotFound("lunch", human, 1L, 25.0, false);
		final RuleModel dinner = createEveningRuleIfNotFound("dinner", human, 1L, 15.0, false);
		final RuleModel max = createMaxRuleIfNotFound("max", breakfast, lunch, dinner);

		final ItemModel map = createItemIfNotFound("map", unit, Arrays.asList(march), 1);
		final ItemModel backpack = createItemIfNotFound("backpack", unit, Arrays.asList(person), 1);
		final ItemModel hat = createItemIfNotFound("sun hat", unit, Arrays.asList(summer), 1);
		final ItemModel umbrella = createItemIfNotFound("umbrella", unit, Arrays.asList(autumn), 1);
		final ItemModel marquee = createItemIfNotFound("marquee", unit, Arrays.asList(sleepover), 1);
		final ItemModel spoon = createItemIfNotFound("spoon", unit, Arrays.asList(max), 1);
		final ItemModel switchblade = createItemIfNotFound("switchblade", unit, Arrays.asList(people), 1);
		final ItemModel lighter = createItemIfNotFound("lighter", unit, Arrays.asList(people), 2);
		final ItemModel camera = createItemIfNotFound("camera", unit, Arrays.asList(distance), 1);
		final ItemModel fuel = createItemIfNotFound("fuel", liter, Arrays.asList(night, winter), 1.5, 7.5);
		final ItemModel rope = createItemIfNotFound("rope", meter, Arrays.asList(people), 6);
		final ItemModel potatoes = createItemIfNotFound("potatoes", kilogram, Arrays.asList(afternoon), 0.9);
		final ItemModel stew = createItemIfNotFound("can of stew", unit, Arrays.asList(afternoon), 1);
		final ItemModel porridge = createItemIfNotFound("canned porridge", unit, Arrays.asList(morning), 1);
		final ItemModel tea = createItemIfNotFound("packet of tea", unit, Arrays.asList(morning, evening), 1, 1);
		final ItemModel leash = createItemIfNotFound("leash", unit, Arrays.asList(canine), 1);
		final ItemModel dry = createItemIfNotFound("dry food", unit, Arrays.asList(daily), 2);
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
    
    private final ItemModel createItemIfNotFound(final String name, final MeasuringUnitModel measuringUnit, final List<RuleModel> rules, final double... quantity) {
    	ItemModel itemModel = itemService.findByName(name);
        if (itemModel == null) {
			itemModel = itemService.createItem(new ItemModel(name, measuringUnit.getName()));
			if (rules != null) {
				int i = 0;
				for (final RuleModel rule: rules) {
					try {
						itemService.addRule(itemModel.getId(), rule.getName(), quantity[i++]);
					}
					catch (ItemNotFoundException e) {
						LOGGER.warn(e.getMessage(), e);
					}
					catch (RuleNotFoundException e) {
						LOGGER.warn(e.getMessage(), e);
					}					
				}
			}
        }
        return itemModel;
    }
    
    private final RuleModel createRuleIfNotFound(final String name) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createRule(name);
        }
        return ruleModel;
    }
    
    private final RuleModel createDailyRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createDailyRule(name, subject.getId(), subjectCount);
        }
        return ruleModel;
    }
    
    private final RuleModel createSubjectRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createSubjectRule(name, subject.getId(), subjectCount);
        }
        return ruleModel;
    }
    
    private final RuleModel createDistanceRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createDistanceRule(name, subject.getId(), subjectCount, distance);
        }
        return ruleModel;
    }
    
    private final RuleModel createWinterRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createWinterRule(name, subject.getId(), subjectCount, null, false);
        }
        return ruleModel;
    }
    
    private final RuleModel createSpringRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createSpringRule(name, subject.getId(), subjectCount, null, false);
        }
        return ruleModel;
    }
    
    private final RuleModel createSummerRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createSummerRule(name, subject.getId(), subjectCount, null, false);
        }
        return ruleModel;
    }
    
    private final RuleModel createAutumnRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createAutumnRule(name, subject.getId(), subjectCount, null, false);
        }
        return ruleModel;
    }

    private final RuleModel createMorningRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance, final boolean toEvery) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createMorningRule(name, subject.getId(), subjectCount, distance, toEvery);
        }
        return ruleModel;
	}

    private final RuleModel createAfternoonRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance, final boolean toEvery) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createAfternoonRule(name, subject.getId(), subjectCount, distance, toEvery);
        }
        return ruleModel;
	}

    private final RuleModel createEveningRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance, final boolean toEvery) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createEveningRule(name, subject.getId(), subjectCount, distance, toEvery);
        }
        return ruleModel;
	}

    private final RuleModel createNightRuleIfNotFound(final String name, final SubjectModel subject, final Long subjectCount, final Double distance, final boolean toEvery) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createNightRule(name, subject.getId(), subjectCount, distance, toEvery);
        }
        return ruleModel;
	}
    
	private final RuleModel createMinRuleIfNotFound(final String name, final RuleModel... rules) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createMinRule(name, rules);
        }
        return ruleModel;
	}
    
	private final RuleModel createMaxRuleIfNotFound(final String name, final RuleModel... rules) {
    	RuleModel ruleModel = ruleService.findByName(name);
        if (ruleModel == null) {
			ruleModel = ruleService.createMaxRule(name, rules);
        }
        return ruleModel;
	}
}
