package hkmu.comps380f.controller;

import hkmu.comps380f.dao.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/index")

public class IndexController {

    @Resource
    private UserRepository UserRepo;

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/lecturer")
//    public String lecturerIndex(ModelMap model) {
//        model.addAttribute("Users", UserRepo.findAll());
//        return "lecturerIndex";
//    }

    @GetMapping("")
    public String userIndex(ModelMap model) {
        model.addAttribute("Users", UserRepo.findAll());
        return "indexLecturers";
    }


}
