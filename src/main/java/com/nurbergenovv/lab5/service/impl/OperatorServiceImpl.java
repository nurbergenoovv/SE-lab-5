package com.nurbergenovv.lab5.service.impl;

import com.nurbergenovv.lab5.entity.Operator;
import com.nurbergenovv.lab5.repository.OperatorRepository;
import com.nurbergenovv.lab5.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final OperatorRepository operatorRepository;


    @Override
    public void create(Operator operator) {
        if (operator == null) {
            throw new IllegalArgumentException("Operator is null");
        }
        operator.setRequests(null);
       operatorRepository.save(operator);
    }

    @Override
    public Operator getById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        return operatorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Operator not found: " + id));
    }

    @Override
    public List<Operator> getAll() {
        return operatorRepository.findAll();
    }

    @Override
    public void update(Long id, Operator updated) {
        if (id == null) throw new IllegalArgumentException("id is null");
        if (updated == null) throw new IllegalArgumentException("updated operator is null");

        Operator existing = operatorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Operator not found: " + id));

        existing.setName(updated.getName());
        existing.setSurname(updated.getSurname());
        existing.setDepartment(updated.getDepartment());
        operatorRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        if (!operatorRepository.existsById(id)) {
            throw new IllegalArgumentException("Operator not found: " + id);
        }
        operatorRepository.deleteById(id);
    }
}