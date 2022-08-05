package com.tietoevry.walk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tietoevry.walk.entity.MeasuringUnit;
import com.tietoevry.walk.exceptions.MeasuringUnitNotFoundException;
import com.tietoevry.walk.form.MeasuringUnitModel;
import com.tietoevry.walk.repository.MeasuringUnitRepository;

@Service
public class MeasuringUnitService {

    @Autowired
    private MeasuringUnitRepository measuringUnitRepository;

    public List<MeasuringUnitModel> listMeasuringUnits() {
    	final List<MeasuringUnit> measuringUnitList = (List<MeasuringUnit>)measuringUnitRepository.findAll();
        return measuringUnitList.stream().map(MeasuringUnitService::assembleMeasuringUnitModel).collect(Collectors.toList());
    }

    public MeasuringUnitModel createMeasuringUnit(final String name) {
    	final MeasuringUnit measuringUnitEntity = new MeasuringUnit();
        measuringUnitEntity.setName(name);
        final MeasuringUnit mu = measuringUnitRepository.save(measuringUnitEntity);
        return assembleMeasuringUnitModel(mu);
    }

    public MeasuringUnitModel createMeasuringUnit(final MeasuringUnitModel measuringUnitModel) {
    	if (measuringUnitModel == null)
    		throw new IllegalArgumentException("measuringUnitModel");
    	final MeasuringUnit measuringUnitEntity = new MeasuringUnit();
        measuringUnitEntity.setName(measuringUnitModel.getName());
        measuringUnitEntity.setDescription(measuringUnitModel.getDescription());
        final MeasuringUnit mu = measuringUnitRepository.save(measuringUnitEntity);
        return assembleMeasuringUnitModel(mu);
    }

    public MeasuringUnitModel saveMeasuringUnit(final MeasuringUnitModel measuringUnitModel) throws MeasuringUnitNotFoundException {
    	if (measuringUnitModel == null)
    		throw new IllegalArgumentException("measuringUnitModel");
        MeasuringUnit measuringUnitEntity;
        final Long id = measuringUnitModel.getId();
        if (id != null) {
            Optional<MeasuringUnit> measuringUnitOptional = measuringUnitRepository.findById(id);
            if (measuringUnitOptional.isPresent()) {
                measuringUnitEntity = measuringUnitOptional.get();
            }
            else {
                throw new MeasuringUnitNotFoundException(id);
            }
        }
        else {
            measuringUnitEntity = new MeasuringUnit();
        }
        measuringUnitEntity.setName(measuringUnitModel.getName());
        measuringUnitEntity.setDescription(measuringUnitModel.getDescription());
        final MeasuringUnit mu = measuringUnitRepository.save(measuringUnitEntity);
        return assembleMeasuringUnitModel(mu);
    }

    public MeasuringUnitModel findMeasuringUnit(final Long id) {
        MeasuringUnitModel measuringUnitModel = null;
        final Optional<MeasuringUnit> measuringUnitEntity = measuringUnitRepository.findById(id);
        if (measuringUnitEntity.isPresent()) {
        	final MeasuringUnit mu = measuringUnitEntity.get();
            measuringUnitModel = assembleMeasuringUnitModel(mu);
        }
        return measuringUnitModel;
    }

    public MeasuringUnitModel findByName(final String name) {
        MeasuringUnitModel measuringUnitModel = null;
        final Optional<MeasuringUnit> measuringUnitEntity = measuringUnitRepository.findByName(name);
        if (measuringUnitEntity.isPresent()) {
        	final MeasuringUnit mu = measuringUnitEntity.get();
            measuringUnitModel = assembleMeasuringUnitModel(mu);
        }
        return measuringUnitModel;
    }

    public void deleteMeasuringUnits(final Long[] ids) {
        for (Long id: ids) {
            measuringUnitRepository.deleteById(id);
        }
    }

    private static MeasuringUnitModel assembleMeasuringUnitModel(final MeasuringUnit measuringUnitEntity) {
        return new MeasuringUnitModel(measuringUnitEntity.getId(), measuringUnitEntity.getName(), measuringUnitEntity.getDescription());
    }
}
