package hkmu.comps380f.controller;

import hkmu.comps380f.dao.PollRepository;
import hkmu.comps380f.model.PollComment;
import hkmu.comps380f.model.User_choice;
import org.springframework.security.core.Authentication;
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

    public static class commentForm {

        private String user_name;
        private String comment;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }

    @GetMapping("")
    public ModelAndView viewPoll(@PathVariable("poll_id") String poll_id, Model model, Authentication authentication) {
        model.addAttribute("poll", PollRepo.findPoll(Integer.parseInt(poll_id)));
        User_choice findUser = PollRepo.findUser(Integer.parseInt(poll_id), authentication.getName());
        model.addAttribute("pollComments",PollRepo.findPollComment(Integer.parseInt(poll_id)));
        if (!findUser.getUser_choice().isEmpty()) {
            model.addAttribute("findChoice_a", PollRepo.findChoice_a(Integer.parseInt(poll_id)));
            model.addAttribute("findChoice_b", PollRepo.findChoice_b(Integer.parseInt(poll_id)));
            model.addAttribute("findChoice_c", PollRepo.findChoice_c(Integer.parseInt(poll_id)));
            model.addAttribute("findChoice_d", PollRepo.findChoice_d(Integer.parseInt(poll_id)));
            model.addAttribute("checked", true);
            model.addAttribute("Choose_" + findUser.getUser_choice(), "checked");

        }
        return new ModelAndView("poll", "poll_choice", new pollForm());
    }

    @PostMapping("")
    public ModelAndView editPollHandle(PollController.pollForm form, Model model, @PathVariable("poll_id") String poll_id, Authentication authentication) {
        User_choice findUser = PollRepo.findUser(Integer.parseInt(poll_id), authentication.getName());
        if (!findUser.getUser_choice().isEmpty()) {
            PollRepo.editUser_choice(authentication.getName(), form.getUser_choice(), Integer.parseInt(poll_id));
        } else {
            PollRepo.addUser_choice(authentication.getName(), form.getUser_choice(), Integer.parseInt(poll_id));
        }
        return new ModelAndView("redirect:/poll" + poll_id);
    }

    @GetMapping("/comment/add")
    public ModelAndView addLectureComment(@PathVariable String poll_id,Model model) {
        model.addAttribute("id", poll_id);
        return new ModelAndView("pollCommentAdd", "commentForm", new commentForm());
    }

    @PostMapping("/comment/add")
    public ModelAndView addLectureCommentHandle(commentForm form, Model model,
                                                @PathVariable("poll_id") String poll_id, Authentication auth) {
        PollComment lc = new PollComment(Integer.parseInt(poll_id), form.getComment(), auth.getName());
        PollRepo.addPollComment(lc);
        return new ModelAndView("redirect:/poll" + poll_id);
    }

    @GetMapping("/delete/pollComment/{comment_id}")
    public View deletePollComment(@PathVariable("comment_id") String comment_id, @PathVariable String poll_id) {
        PollRepo.deleteComment(Integer.parseInt(comment_id));
        return new RedirectView("/poll"+poll_id, true);
    }

    @GetMapping("/delete")
    public View deletePoll(@PathVariable("poll_id") String poll_id) {
        PollRepo.delete(Integer.parseInt(poll_id));
        return new RedirectView("/poll"+poll_id, true);
    }
}
