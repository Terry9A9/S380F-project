package hkmu.comps380f.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RootController {
    @GetMapping("")
    public View index() {
        return new RedirectView("/index", true);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/Registration")
    public String Registration() {
        return "login";
    }

    @GetMapping("/favicon.ico")
    public View faviconBug() {
        return new RedirectView("/index", true);
    }
}
