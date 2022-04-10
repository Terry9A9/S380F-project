package hkmu.comps380f.controller;

import hkmu.comps380f.dao.StudentRepository;
import hkmu.comps380f.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Resource
    private StudentRepository StudentRepo;

    @GetMapping("")
    public ModelAndView Registration() {
        return new ModelAndView("registration", "Student", new Student());
    }

    @PostMapping("")
    public ModelAndView addStudentHandle(Student e, Model model) {
        boolean success = true;
        try{
            StudentRepo.addEntry(e);
        }catch (Exception DerbySQLIntegrityConstraintViolationException){
            success = false;
        }
        if (success){
            return new ModelAndView("redirect:/login?regSuccessful");
        }else{
            model.addAttribute("error", "Username already exists");
            return new ModelAndView("registration","Student", new Student());
        }
    }
}
