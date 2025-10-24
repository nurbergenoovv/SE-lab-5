package com.nurbergenovv.lab5.service;

import com.nurbergenovv.lab5.entity.ApplicationRequest;
import com.nurbergenovv.lab5.entity.Operator;

import java.util.List;

public interface ApplicationRequestService {
    public void assignOperators(Long ARequestId, List<Long> operatorsIds);

    public void removeOperator(Long requestId, Long operatorId);

    public List<ApplicationRequest> getAll();

    public void addApplicationRequest(ApplicationRequest request);

    public void deleteApplicationRequest(ApplicationRequest request);

    public ApplicationRequest getApplicationRequestById(Long id);

    public List<Operator> getAllOperatorsByApplicationRequestId(Long id);

    public void handleApplicationRequest(ApplicationRequest applicationRequest);
}