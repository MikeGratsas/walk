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
        List<MeasuringUnit> measuringUnitList = (List<MeasuringUnit>)measuringUnitRepository.findAll();
        return measuringUnitList.stream().map(MeasuringUnitService::assembleMeasuringUnitModel).collect(Collectors.toList());
    }

    public MeasuringUnitModel createMeasuringUnit(String name) {
        MeasuringUnit measuringUnitEntity = new MeasuringUnit();
        measuringUnitEntity.setName(name);
        MeasuringUnit c = measuringUnitRepository.save(measuringUnitEntity);
        return assembleMeasuringUnitModel(c);
    }

    public MeasuringUnitModel createMeasuringUnit(MeasuringUnitModel measuringUnitModel) {
    	if (measuringUnitModel == null)
    		throw new IllegalArgumentException("measuringUnitModel");
        MeasuringUnit measuringUnitEntity = new MeasuringUnit();
        measuringUnitEntity.setName(measuringUnitModel.getName());
        measuringUnitEntity.setDescription(measuringUnitModel.getDescription());
        MeasuringUnit c = measuringUnitRepository.save(measuringUnitEntity);
        return assembleMeasuringUnitModel(c);
    }

    public MeasuringUnitModel saveMeasuringUnit(MeasuringUnitModel measuringUnitModel) throws MeasuringUnitNotFoundException {
    	if (measuringUnitModel == null)
    		throw new IllegalArgumentException("measuringUnitModel");
        MeasuringUnit measuringUnitEntity;
        Long id = measuringUnitModel.getId();
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
        MeasuringUnit c = measuringUnitRepository.save(measuringUnitEntity);
        return assembleMeasuringUnitModel(c);
    }

    public MeasuringUnitModel findMeasuringUnit(Long id) {
        MeasuringUnitModel measuringUnitModel = null;
        Optional<MeasuringUnit> measuringUnitEntity = measuringUnitRepository.findById(id);
        if (measuringUnitEntity.isPresent()) {
            MeasuringUnit mu = measuringUnitEntity.get();
            measuringUnitModel = assembleMeasuringUnitModel(mu);
        }
        return measuringUnitModel;
    }

    public MeasuringUnitModel findByName(String name) {
        MeasuringUnitModel measuringUnitModel = null;
        Optional<MeasuringUnit> measuringUnitEntity = measuringUnitRepository.findByName(name);
        if (measuringUnitEntity.isPresent()) {
            MeasuringUnit mu = measuringUnitEntity.get();
            measuringUnitModel = assembleMeasuringUnitModel(mu);
        }
        return measuringUnitModel;
    }

    public void deleteMeasuringUnits(Long[] ids) {
        for (Long id: ids) {
            measuringUnitRepository.deleteById(id);
        }
    }

    private static MeasuringUnitModel assembleMeasuringUnitModel(MeasuringUnit measuringUnitEntity) {
        return new MeasuringUnitModel(measuringUnitEntity.getId(), measuringUnitEntity.getName(), measuringUnitEntity.getDescription());
    }
}
