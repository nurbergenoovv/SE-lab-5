package com.nurbergenovv.lab5.controller;

import com.nurbergenovv.lab5.entity.ApplicationRequest;
import com.nurbergenovv.lab5.entity.Course;
import com.nurbergenovv.lab5.entity.Operator;
import com.nurbergenovv.lab5.service.ApplicationRequestService;
import com.nurbergenovv.lab5.service.CourseService;
import com.nurbergenovv.lab5.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
public class ApplicationRequestController {

    private final ApplicationRequestService requestService;
    private final CourseService courseService;
    private final OperatorService operatorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("requests", requestService.getAll());
        return "requests";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("request", new ApplicationRequest());
        model.addAttribute("courses", courseService.getAllCourses());
        return "request_form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ApplicationRequest request) {
        requestService.addApplicationRequest(request);
        return "redirect:/requests";
    }

    @GetMapping("/{id}/assign")
    public String assignForm(@PathVariable Long id, Model model) {
        ApplicationRequest r = requestService.getApplicationRequestById(id);
        List<Operator> allOperators = operatorService.getAll();
        model.addAttribute("request", r);
        model.addAttribute("operators", allOperators);
        return "assign_operators";
    }

    @PostMapping("/{id}/assign")
    public String assign(@PathVariable Long id, @RequestParam(value = "operatorIds", required = false) List<Long> operatorIds) {
        if (operatorIds != null && !operatorIds.isEmpty()) {
            requestService.assignOperators(id, operatorIds);
        }
        return "redirect:/requests";
    }

    @PostMapping("/{id}/remove-operator")
    public String removeOperator(@PathVariable Long id, @RequestParam Long operatorId) {
        requestService.removeOperator(id, operatorId);
        return "redirect:/requests/" + id + "/assign";
    }

    @PostMapping("/{id}/handle")
    public String handle(@PathVariable Long id) {
        ApplicationRequest r = requestService.getApplicationRequestById(id);
        requestService.handleApplicationRequest(r);
        return "redirect:/requests";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        ApplicationRequest r = requestService.getApplicationRequestById(id);
        requestService.deleteApplicationRequest(r);
        return "redirect:/requests";
    }
}