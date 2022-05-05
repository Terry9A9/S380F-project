package hkmu.comps380f.controller;

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.springframework.security.core.Authentication;
import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.LectureRepository;
import hkmu.comps380f.dao.UserRepository;
import hkmu.comps380f.model.Course;
import hkmu.comps380f.model.WebUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/index")

public class IndexController {
    @Resource
    private UserRepository UserRepo;
    @Resource
    private LectureRepository LectureRepo;
    @Resource
    private CourseRepository CourseRepo;

    public static class Form {

        private String course_code;
        private String course_name;

        public String getCourse_code() {
            return course_code;
        }

        public void setCourse_code(String course_code) {
            this.course_code = course_code;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }
    }

    @GetMapping("")
    public String userIndex(ModelMap model) {
        model.addAttribute("Users", UserRepo.findAll());
        model.addAttribute("Lectures", LectureRepo.findAll());
        model.addAttribute("Courses", CourseRepo.findAll());
        return "indexLecturers";
    }

    @GetMapping("/addCourse")
    public ModelAndView addCourse() {
        return new ModelAndView("addCourse", "Course", new Form());
    }

    @PostMapping("/addCourse")
    public ModelAndView addCourseHandle(Form form, Model model) throws DerbySQLIntegrityConstraintViolationException {
        boolean success = true;
        CourseRepo.addCourse(form.getCourse_code(), form.getCourse_name());
        return new ModelAndView("redirect:/index?addSuccessful");
    }
}
