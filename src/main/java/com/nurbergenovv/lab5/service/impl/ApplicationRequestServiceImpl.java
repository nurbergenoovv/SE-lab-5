package com.nurbergenovv.lab5.service.impl;

import com.nurbergenovv.lab5.entity.ApplicationRequest;
import com.nurbergenovv.lab5.entity.Operator;
import com.nurbergenovv.lab5.entity.Course;
import com.nurbergenovv.lab5.repository.ApplicationRequestRepository;
import com.nurbergenovv.lab5.repository.OperatorRepository;
import com.nurbergenovv.lab5.repository.CourseRepository;
import com.nurbergenovv.lab5.service.ApplicationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ApplicationRequestServiceImpl implements ApplicationRequestService {

    private final ApplicationRequestRepository requestRepo;
    private final OperatorRepository operatorRepo;
    private final CourseRepository courseRepo;

    @Override
    public void assignOperators(Long requestId, List<Long> operatorsIds) {
        ApplicationRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("ApplicationRequest not found: " + requestId));

        if (req.isHandled()) {
            throw new IllegalStateException("Request already handled and cannot be reassigned: " + requestId);
        }

        List<Operator> ops = operatorRepo.findAllById(operatorsIds);
        if (ops.size() != operatorsIds.size()) {
            throw new IllegalArgumentException("Some operator ids not found. Requested: "
                    + operatorsIds + ", found: " + ops.stream().map(Operator::getId).toList());
        }

        if (req.getOperators() == null) {
            req.setOperators(new ArrayList<>());
        }

        req.getOperators().addAll(ops);
        req.setHandled(true);
        requestRepo.save(req);
    }

    @Override
    public List<ApplicationRequest> getAll(){
        return requestRepo.findAll();
    }

    @Override
    public void removeOperator(Long requestId, Long operatorId) {
        ApplicationRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("ApplicationRequest not found: " + requestId));

        if (req.getOperators() == null || req.getOperators().isEmpty()) {
            return;
        }

        boolean removed = req.getOperators().removeIf(o -> o.getId().equals(operatorId));
        if (removed) {
            requestRepo.save(req);
        }
    }

    @Override
    public void addApplicationRequest(ApplicationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ApplicationRequest is null");
        }

        if (request.getCourse() != null && request.getCourse().getId() != null) {
            Long courseId = request.getCourse().getId();
            Course c = courseRepo.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
            request.setCourse(c);
        }

        request.setOperators(new ArrayList<>());
        request.setHandled(false);
        requestRepo.save(request);
    }

    @Override
    public void deleteApplicationRequest(ApplicationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ApplicationRequest is null");
        }
        if (request.getId() == null) {
            throw new IllegalArgumentException("ApplicationRequest id is null");
        }
        requestRepo.deleteById(request.getId());
    }

    @Override
    public ApplicationRequest getApplicationRequestById(Long id) {
        return requestRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ApplicationRequest not found: " + id));
    }

    @Override
    public List<Operator> getAllOperatorsByApplicationRequestId(Long id) {
        ApplicationRequest req = requestRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ApplicationRequest not found: " + id));
        if (req.getOperators() == null) return new ArrayList<>();
        return new ArrayList<>(req.getOperators());
    }

    @Override
    public void handleApplicationRequest(ApplicationRequest applicationRequest) {
        if (applicationRequest == null || applicationRequest.getId() == null) {
            throw new IllegalArgumentException("ApplicationRequest is null or id is null");
        }
        ApplicationRequest req = requestRepo.findById(applicationRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("ApplicationRequest not found: " + applicationRequest.getId()));
        req.setHandled(true);
        requestRepo.save(req);
    }
}