package com.nurbergenovv.lab5.service;

import com.nurbergenovv.lab5.entity.ApplicationRequest;
import com.nurbergenovv.lab5.entity.Course;

import java.util.List;


public interface CourseService {

    public void addCourse(Course course);

    public Course getCourseById(Long id);

    public List<Course> getAllCourses();

    public void update(Long id, Course updated);

    public void deleteCourse(Course course);

    public List<ApplicationRequest> getAllApplicationRequests(Course course);
}
