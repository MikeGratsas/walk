package com.tietoevry.walk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tietoevry.walk.entity.*;
import com.tietoevry.walk.exceptions.RuleNotFoundException;
import com.tietoevry.walk.exceptions.RuleUpdatedException;
import com.tietoevry.walk.form.RuleModel;
import com.tietoevry.walk.repository.RuleRepository;
import com.tietoevry.walk.repository.SubjectRepository;

@Service
@Transactional
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public List<RuleModel> listRules() {
        List<Rule> ruleList = (List<Rule>)ruleRepository.findAll();
        return ruleList.stream().map(RuleService::assembleRuleModel).collect(Collectors.toList());
    }

    public RuleModel createRule(String name) {
        return addRule(new Rule(name));
    }

    public RuleModel createSubjectRule(String name, Long subjectId, Long subjectCount) {
    	return addSubjectRule(new SubjectRule(name), subjectId, subjectCount);
    }

    public RuleModel createDistanceRule(String name, Long subjectId, Long subjectCount, Double distance) {
    	return addDistanceRule(new DistanceRule(name), subjectId, subjectCount, distance);
    }

    public RuleModel createWinterRule(String name, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	return addSeasonRule(new WinterRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createSpringRule(String name, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	return addSeasonRule(new SpringRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createSummerRule(String name, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	return addSeasonRule(new SummerRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createAutumnRule(String name, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	return addSeasonRule(new AutumnRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createMorningRule(String name, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	return addDayTimeRule(new MorningRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createAfternoonRule(String name, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	return addDayTimeRule(new AfternoonRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createEveningRule(String name, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	return addDayTimeRule(new EveningRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel createNightRule(String name, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	return addDayTimeRule(new NightRule(name), subjectId, subjectCount, distance, toEvery);
    }

    public RuleModel saveRule(RuleModel ruleModel) throws RuleNotFoundException, RuleUpdatedException {
        Rule ruleEntity;
        Long id = ruleModel.getId();
        if (id != null) {
            Optional<Rule> ruleOptional = ruleRepository.findById(id);
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
        Optional<Rule> ruleEntity = ruleRepository.findById(id);
        if (ruleEntity.isPresent()) {
            Rule rule = ruleEntity.get();
            ruleModel = assembleRuleModel(rule);
        }
        return ruleModel;
    }

    public RuleModel findByName(String name) {
        RuleModel ruleModel = null;
        Optional<Rule> ruleEntity = ruleRepository.findByName(name);
        if (ruleEntity.isPresent()) {
            Rule rule = ruleEntity.get();
            ruleModel = assembleRuleModel(rule);
        }
        return ruleModel;
    }

    public void deleteRules(Long[] ids) {
        for (Long id: ids) {
            ruleRepository.deleteById(id);
        }
    }

	private RuleModel addRule(Rule ruleEntity) {
		Rule rule = ruleRepository.save(ruleEntity);
        return assembleRuleModel(rule);
	}

	private RuleModel addSubjectRule(SubjectRule ruleEntity, Long subjectId, Long subjectCount) {
		Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
    	if (subjectOptional.isPresent()) {
    		ruleEntity.setSubject(subjectOptional.get());
    	}
    	ruleEntity.setSubjectCount(subjectCount);
        return addRule(ruleEntity);
	}

	private RuleModel addDistanceRule(DistanceRule ruleEntity, Long subjectId, Long subjectCount, Double distance) {
    	ruleEntity.setDistance(distance);
        return addSubjectRule(ruleEntity, subjectId, subjectCount);
	}

	private RuleModel addSeasonRule(SeasonRule ruleEntity, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	ruleEntity.setToEvery(toEvery);
        return addDistanceRule(ruleEntity, subjectId, subjectCount, distance);
	}

	private RuleModel addDayTimeRule(DayTimeRule ruleEntity, Long subjectId, Long subjectCount, Double distance, boolean toEvery) {
    	ruleEntity.setToEvery(toEvery);
        return addDistanceRule(ruleEntity, subjectId, subjectCount, distance);
	}

	private static RuleModel assembleRuleModel(Rule ruleEntity) {
        return new RuleModel(ruleEntity.getId(), ruleEntity.getName(), ruleEntity.getCreated(), ruleEntity.getLastUpdated());
    }
}
