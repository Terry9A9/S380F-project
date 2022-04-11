package hkmu.comps380f.controller;

import hkmu.comps380f.dao.UsersRepository;
import hkmu.comps380f.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Resource
    private UsersRepository StudentRepo;

    @GetMapping("")
    public ModelAndView Registration() {
        return new ModelAndView("registration", "Users", new Users());
    }

    @PostMapping("")
    public ModelAndView addStudentHandle(Users e, Model model) {
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
            return new ModelAndView("registration","Users", new Users());
        }
    }
}
