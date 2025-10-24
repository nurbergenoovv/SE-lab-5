package com.nurbergenovv.lab5.service.impl;

import com.nurbergenovv.lab5.entity.ApplicationRequest;
import com.nurbergenovv.lab5.entity.Course;
import com.nurbergenovv.lab5.repository.ApplicationRequestRepository;
import com.nurbergenovv.lab5.repository.CourseRepository;
import com.nurbergenovv.lab5.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ApplicationRequestRepository applicationRequestRepository;

    @Override
    public void addCourse(Course course) {
        if (course == null) throw new IllegalArgumentException("Course is null");
        course.setApplicationRequests(new ArrayList<>());
        courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void update(Long id, Course updated) {
        if (id == null) throw new IllegalArgumentException("id is null");
        if (updated == null) throw new IllegalArgumentException("updated course is null");

        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        courseRepository.save(existing);
    }

    @Override
    public void deleteCourse(Course course) {
        if (course == null) throw new IllegalArgumentException("Course is null");
        Long id = course.getId();
        if (id == null) throw new IllegalArgumentException("Course id is null");
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course not found: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public List<ApplicationRequest> getAllApplicationRequests(Course course) {
        if (course == null) throw new IllegalArgumentException("Course is null");
        Long id = course.getId();
        if (id == null) throw new IllegalArgumentException("Course id is null");

        Course persisted = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));

        if (persisted.getApplicationRequests() == null) return new ArrayList<>();
        return new ArrayList<>(persisted.getApplicationRequests());
    }
}