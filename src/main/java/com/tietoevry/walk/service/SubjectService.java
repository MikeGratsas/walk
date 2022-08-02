package com.tietoevry.walk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tietoevry.walk.entity.Subject;
import com.tietoevry.walk.exceptions.SubjectNotFoundException;
import com.tietoevry.walk.form.SubjectModel;
import com.tietoevry.walk.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectModel> listSubjects() {
        List<Subject> subjectList = (List<Subject>)subjectRepository.findAll();
        return subjectList.stream().map(SubjectService::assembleSubjectModel).collect(Collectors.toList());
    }

    public SubjectModel createSubject(String name) {
        Subject subjectEntity = new Subject();
        subjectEntity.setName(name);
        Subject c = subjectRepository.save(subjectEntity);
        return assembleSubjectModel(c);
    }

    public SubjectModel createSubject(SubjectModel subjectModel) {
        Subject subjectEntity = new Subject();
        subjectEntity.setName(subjectModel.getName());
        Subject c = subjectRepository.save(subjectEntity);
        return assembleSubjectModel(c);
    }

    public SubjectModel saveSubject(SubjectModel subjectModel) throws SubjectNotFoundException {
        Subject subjectEntity;
        Long id = subjectModel.getId();
        if (id != null) {
            Optional<Subject> subjectOptional = subjectRepository.findById(id);
            if (subjectOptional.isPresent()) {
                subjectEntity = subjectOptional.get();
            }
            else {
                throw new SubjectNotFoundException(id);
            }
        }
        else {
            subjectEntity = new Subject();
        }
        subjectEntity.setName(subjectModel.getName());
        Subject c = subjectRepository.save(subjectEntity);
        return assembleSubjectModel(c);
    }

    public SubjectModel findSubject(Long id) {
        SubjectModel subjectModel = null;
        Optional<Subject> subjectEntity = subjectRepository.findById(id);
        if (subjectEntity.isPresent()) {
            Subject mu = subjectEntity.get();
            subjectModel = assembleSubjectModel(mu);
        }
        return subjectModel;
    }

    public SubjectModel findByName(String name) {
        SubjectModel subjectModel = null;
        Optional<Subject> subjectEntity = subjectRepository.findByName(name);
        if (subjectEntity.isPresent()) {
            Subject mu = subjectEntity.get();
            subjectModel = assembleSubjectModel(mu);
        }
        return subjectModel;
    }

    public void deleteSubjects(Long[] ids) {
        for (Long id: ids) {
            subjectRepository.deleteById(id);
        }
    }

    private static SubjectModel assembleSubjectModel(Subject subjectEntity) {
        return new SubjectModel(subjectEntity.getId(), subjectEntity.getName());
    }
}
