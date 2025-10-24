package com.nurbergenovv.lab5.controller;

import com.nurbergenovv.lab5.entity.Operator;
import com.nurbergenovv.lab5.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/operators")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("operators", operatorService.getAll());
        return "operators";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("operator", new Operator());
        return "operator_form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Operator operator) {
        operatorService.create(operator);
        return "redirect:/operators";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("operator", operatorService.getById(id));
        return "operator_form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute Operator operator) {
        operatorService.update(id, operator);
        return "redirect:/operators";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        operatorService.delete(id);
        return "redirect:/operators";
    }
}