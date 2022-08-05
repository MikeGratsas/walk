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

    public SubjectModel createSubject(final String name) {
    	final Subject subjectEntity = new Subject();
        subjectEntity.setName(name);
        Subject s = subjectRepository.save(subjectEntity);
        return assembleSubjectModel(s);
    }

    public SubjectModel createSubject(final SubjectModel subjectModel) {
    	final Subject subjectEntity = new Subject();
        subjectEntity.setName(subjectModel.getName());
        final Subject s = subjectRepository.save(subjectEntity);
        return assembleSubjectModel(s);
    }

    public SubjectModel saveSubject(final SubjectModel subjectModel) throws SubjectNotFoundException {
        Subject subjectEntity;
        final Long id = subjectModel.getId();
        if (id != null) {
        	final Optional<Subject> subjectOptional = subjectRepository.findById(id);
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
        final Subject s = subjectRepository.save(subjectEntity);
        return assembleSubjectModel(s);
    }

    public SubjectModel findSubject(final Long id) {
        SubjectModel subjectModel = null;
        final Optional<Subject> subjectEntity = subjectRepository.findById(id);
        if (subjectEntity.isPresent()) {
            Subject s = subjectEntity.get();
            subjectModel = assembleSubjectModel(s);
        }
        return subjectModel;
    }

    public SubjectModel findByName(final String name) {
        SubjectModel subjectModel = null;
        final Optional<Subject> subjectEntity = subjectRepository.findByName(name);
        if (subjectEntity.isPresent()) {
            Subject s = subjectEntity.get();
            subjectModel = assembleSubjectModel(s);
        }
        return subjectModel;
    }

    public void deleteSubjects(final Long[] ids) {
        for (Long id: ids) {
            subjectRepository.deleteById(id);
        }
    }

    private static SubjectModel assembleSubjectModel(final Subject subjectEntity) {
        return new SubjectModel(subjectEntity.getId(), subjectEntity.getName());
    }
}
