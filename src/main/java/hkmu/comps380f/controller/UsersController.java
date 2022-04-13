package hkmu.comps380f.controller;

import hkmu.comps380f.dao.UserRepository;
import hkmu.comps380f.model.WebUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UsersController {
    @Resource
    private UserRepository UserRepo;

    public static class Form{
        private String username;
        private String password;
        private String fullName;
        private String phoneNumber;
        private String address;
        private String[] roles;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }

    @GetMapping("/registration")
    public ModelAndView Registration() {
        return new ModelAndView("registration", "User", new Form());
    }

    @PostMapping("/registration")
    public ModelAndView addStudentHandle(Form form, Model model) {

        boolean success = true;
        try{
            WebUser user = new WebUser(form.getUsername(), form.getPassword(), form.fullName,form.phoneNumber,
                    form.getAddress(), form.getRoles());
            UserRepo.addUser(user);
        }catch (Exception DerbySQLIntegrityConstraintViolationException){
            success = false;
        }
        if (success){
            return new ModelAndView("redirect:/login?regSuccessful");
        }else{
            model.addAttribute("error", "Username already exists");
            return new ModelAndView("registration","User", new Form());
        }
    }

    @GetMapping("/edit/{username}")
    public String queryUser(@PathVariable("username") String username, Model model) {
        model.addAttribute("UserInfo",UserRepo.findUser(username));
        return "registration";
    }

    @PostMapping("/edit/{username}")
    public String queryUser(Form form, Model model) {
        WebUser user = new WebUser(form.getUsername(), form.getPassword(), form.fullName,form.phoneNumber,
                form.getAddress(), form.getRoles());
        UserRepo.addUser(user);
        return "registration";
    }


    @GetMapping("/delete/{username}")
    public View deleteUser(@PathVariable("username") String username) {
        UserRepo.delete(username);
        return new RedirectView("/index?successful", true);
    }
}
