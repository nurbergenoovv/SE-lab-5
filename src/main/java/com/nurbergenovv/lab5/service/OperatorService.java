package com.nurbergenovv.lab5.service;


import com.nurbergenovv.lab5.entity.Operator;

import java.util.List;

public interface OperatorService {
    public void create(Operator operator);
    public Operator getById(Long id);
    public List<Operator> getAll();
    public void update(Long id, Operator updated);
    public void delete(Long id);
}
