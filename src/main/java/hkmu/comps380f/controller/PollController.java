package hkmu.comps380f.controller;

import hkmu.comps380f.dao.PollRepository;
import hkmu.comps380f.model.User_choice;
import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/poll{poll_id}")
public class PollController {



    @Resource
    private PollRepository PollRepo;

    public static class pollForm {

        private String user_choice;

        public String getUser_choice() {
            return user_choice;
        }

        public void setUser_choice(String user_choice) {
            this.user_choice = user_choice;
        }

    }

    @GetMapping("")
    public ModelAndView viewPoll(@PathVariable("poll_id") String poll_id, ModelMap model, Authentication authentication)  {
        model.addAttribute("poll", PollRepo.findPoll(Integer.parseInt(poll_id)));
        model.addAttribute("findChoice_a", PollRepo.findChoice_a(Integer.parseInt(poll_id)));
        model.addAttribute("findChoice_b", PollRepo.findChoice_b(Integer.parseInt(poll_id)));
        model.addAttribute("findChoice_c", PollRepo.findChoice_c(Integer.parseInt(poll_id)));
        model.addAttribute("findChoice_d", PollRepo.findChoice_d(Integer.parseInt(poll_id)));
        User_choice findUser = PollRepo.findUser(Integer.parseInt(poll_id), authentication.getName());
        if(!findUser.getUser_choice().isEmpty()){
            model.addAttribute("Choose_"+findUser.getUser_choice(),"checked");
        }
        return new ModelAndView("poll", "poll_choice", new pollForm());
    }
}
