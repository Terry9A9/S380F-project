package hkmu.comps380f.model;

import java.io.Serializable;

public class Course implements Serializable {
    private String course_code;
    private String Course_name;

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code.toUpperCase();
    }

    public String getCourse_name() {
        return Course_name;
    }

    public void setCourse_name(String course_name) {
        Course_name = course_name;
    }
}
