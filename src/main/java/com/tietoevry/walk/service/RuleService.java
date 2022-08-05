package com.tietoevry.walk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tietoevry.walk.entity.*;
import com.tietoevry.walk.exceptions.RuleNotFoundException;
import com.tietoevry.walk.exceptions.RuleUpdatedException;
import com.tietoevry.walk.form.RuleModel;
import com.tietoevry.walk.repository.RuleRepository;
import com.tietoevry.walk.repository.SubjectRepository;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public List<RuleModel> listRules() {
        List<Rule> ruleList = (List<Rule>)ruleRepository.findAll();
        return ruleList.stream().map(RuleService::assembleRuleModel).collect(Collectors.toList());
    }

    public RuleModel createRule(final String name) {
        return addRule(new Rule(name));
    }

    public RuleModel createMinRule(final String name, final RuleModel... ruleModels) {
        final MinRule ruleEntity = new MinRule(name);
        addSubRules(ruleEntity.getRules(), ruleModels);
		return addRule(ruleEntity);
    }

    public RuleModel createMaxRule(final String name, final RuleModel... ruleModels) {
        final MaxRule ruleEntity = new MaxRule(name);
        addSubRules(ruleEntity.getRules(), ruleModels);
		return addRule(ruleEntity);
    }

    public RuleModel createSubjectRule(final String name, final Long subjectId, final Long subjectCount) {
    	return addSubjectRule(new SubjectRule(name), subjectId, subjectCount);
    }

    public RuleModel createDailyRule(final String name, final Long subjectId, final Long subjectCount) {
    	return addSubjectRule(new DailyRule(name), subjectId, subjectCount);
    }

    public RuleModel createDistanceRule(final String name, final Long subjectId, final Long subjectCount, final Double distance) {
    	return addDistanceRule(new DistanceRule(name), subjectId, subjectCount, distance);
    }

    public RuleModel createWinterRule(final String name, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	return addSeasonRule(new WinterRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createSpringRule(final String name, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	return addSeasonRule(new SpringRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createSummerRule(final String name, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	return addSeasonRule(new SummerRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createAutumnRule(final String name, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	return addSeasonRule(new AutumnRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createMorningRule(final String name, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	return addDayTimeRule(new MorningRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createAfternoonRule(final String name, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	return addDayTimeRule(new AfternoonRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createEveningRule(final String name, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	return addDayTimeRule(new EveningRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createNightRule(final String name, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	return addDayTimeRule(new NightRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel saveRule(final RuleModel ruleModel) throws RuleNotFoundException, RuleUpdatedException {
        Rule ruleEntity;
        final Long id = ruleModel.getId();
        if (id != null) {
        	final Optional<Rule> ruleOptional = ruleRepository.findById(id);
            if (ruleOptional.isPresent()) {
                ruleEntity = ruleOptional.get();
                if (!ruleEntity.getLastUpdated().equals(ruleModel.getLastUpdated())) {
                    throw new RuleUpdatedException(id);
                }
            }
            else {
                throw new RuleNotFoundException(id);
            }
        }
        else {
            ruleEntity = new Rule();
        }
        ruleEntity.setName(ruleModel.getName());
        return addRule(ruleEntity);
    }

    public RuleModel findRule(Long id) {
        RuleModel ruleModel = null;
        final Optional<Rule> ruleEntity = ruleRepository.findById(id);
        if (ruleEntity.isPresent()) {
        	final Rule rule = ruleEntity.get();
            ruleModel = assembleRuleModel(rule);
        }
        return ruleModel;
    }

    public RuleModel findByName(final String name) {
        RuleModel ruleModel = null;
        final Optional<Rule> ruleEntity = ruleRepository.findByName(name);
        if (ruleEntity.isPresent()) {
            Rule rule = ruleEntity.get();
            ruleModel = assembleRuleModel(rule);
        }
        return ruleModel;
    }

    public void deleteRules(final Long[] ids) {
        for (Long id: ids) {
            ruleRepository.deleteById(id);
        }
    }

	private void addSubRules(final List<Rule> rules, final RuleModel... ruleModels) {
        for (RuleModel ruleModel : ruleModels) {
        	final Optional<Rule> ruleOptional = ruleRepository.findById(ruleModel.getId());
            if (ruleOptional.isPresent()) {
    			rules.add(ruleOptional.get());
            }
		}
	}

	private RuleModel addRule(final Rule ruleEntity) {
		final Rule rule = ruleRepository.save(ruleEntity);
        return assembleRuleModel(rule);
	}

	private RuleModel addSubjectRule(final SubjectRule ruleEntity, final Long subjectId, final Long subjectCount) {
		final Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
    	if (subjectOptional.isPresent()) {
    		ruleEntity.setSubject(subjectOptional.get());
    	}
    	ruleEntity.setSubjectCount(subjectCount);
        return addRule(ruleEntity);
	}

	private RuleModel addDistanceRule(final DistanceRule ruleEntity, final Long subjectId, final Long subjectCount, final Double distance) {
    	ruleEntity.setDistance(distance);
        return addSubjectRule(ruleEntity, subjectId, subjectCount);
	}

	private RuleModel addSeasonRule(final SeasonRule ruleEntity, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	ruleEntity.setToEvery(toEvery);
        return addDistanceRule(ruleEntity, subjectId, subjectCount, distance);
	}

	private RuleModel addDayTimeRule(final DayTimeRule ruleEntity, final Long subjectId, final Long subjectCount, final Double distance, final boolean toEvery) {
    	ruleEntity.setToEvery(toEvery);
        return addDistanceRule(ruleEntity, subjectId, subjectCount, distance);
	}

	private static RuleModel assembleRuleModel(final Rule ruleEntity) {
        return new RuleModel(ruleEntity.getId(), ruleEntity.getName(), ruleEntity.getCreated(), ruleEntity.getLastUpdated());
    }
}
