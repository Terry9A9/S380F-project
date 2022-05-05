package hkmu.comps380f.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RootController {
    @GetMapping("")
    public View index() {
        return new RedirectView("/index", true);
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/login",params="error")
    public ModelAndView loginError(Model model) {
        model.addAttribute("info","Incorrect username or password");
        model.addAttribute("color","red");
        return new ModelAndView("login");
    }

    @GetMapping(value = "/login",params="logout")
    public ModelAndView loginLogout(Model model) {
        model.addAttribute("info","Logout successful");
        model.addAttribute("color","green");
        return new ModelAndView("login");
    }

    @GetMapping(value = "/login",params="regSuccessful")
    public ModelAndView loginReg(Model model) {
        model.addAttribute("info","Registration successful");
        model.addAttribute("color","green");
        return new ModelAndView("login");
    }

    @GetMapping("/favicon.ico")
    public View faviconBug() {
        return new RedirectView("/index", true);
    }
}
