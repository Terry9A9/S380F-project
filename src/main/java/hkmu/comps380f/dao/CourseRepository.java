package hkmu.comps380f.dao;

import hkmu.comps380f.model.Course;

import java.util.List;

public interface CourseRepository {

    public List<Course> findAll();

    public void addCourse(String course_code, String course_name);
}
