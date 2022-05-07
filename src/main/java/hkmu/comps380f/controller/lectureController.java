package hkmu.comps380f.controller;

import hkmu.comps380f.dao.LectureRepository;
import hkmu.comps380f.dao.PollRepository;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.model.LectureComment;
import hkmu.comps380f.model.Poll;
import hkmu.comps380f.view.DownloadView;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/course/{course_id}")
public class lectureController {

    @Resource
    private LectureRepository LectureRepo;
    @Resource
    private PollRepository PollRepo;

    public static class Form {

        private String title;
        private String lecture_num;
        private List<MultipartFile> attachments;

        public String getLecture_num() {
            return lecture_num;
        }

        public void setLecture_num(String lecture_num) {
            this.lecture_num = lecture_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    public static class pollForm {

        private String question;
        private String ans_a;
        private String ans_b;
        private String ans_c;
        private String ans_d;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAns_a() {
            return ans_a;
        }

        public void setAns_a(String ans_a) {
            this.ans_a = ans_a;
        }

        public String getAns_b() {
            return ans_b;
        }

        public void setAns_b(String ans_b) {
            this.ans_b = ans_b;
        }

        public String getAns_c() {
            return ans_c;
        }

        public void setAns_c(String ans_c) {
            this.ans_c = ans_c;
        }

        public String getAns_d() {
            return ans_d;
        }

        public void setAns_d(String ans_d) {
            this.ans_d = ans_d;
        }
    }

    @GetMapping("/add")
    public ModelAndView addLecture() {
        return new ModelAndView("lectureAdd", "lectureForm", new Form());
    }

    @PostMapping("/add")
    public ModelAndView addLectureHandle(Form form, Model model, @PathVariable("course_id") String course_id) {
        boolean success = true;
        try {
            Lecture l = new Lecture(form.getLecture_num(), form.getTitle(), course_id);
            LectureRepo.addLecture(l, form.getAttachments());
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        if (success) {
            return new ModelAndView("redirect:/index?addSuccessful");
        } else {
            model.addAttribute("error", "Lecture number already exists");
            return new ModelAndView("lectureAdd", "lectureForm", new Form());
        }
    }

    @GetMapping("/ID{lecture_id}")
    public String viewLecture(@PathVariable("lecture_id") String lecture_id, ModelMap model, @PathVariable String course_id) {
        model.addAttribute("lectureInfo", LectureRepo.findLecture(Integer.parseInt(lecture_id)));
        model.addAttribute("lectureComments", LectureRepo.findLectureComment(Integer.parseInt(lecture_id)));
        return "lectureView";
    }

    @GetMapping("/ID{lecture_id}/attachment/{attachment:.+}")
    public View download(@PathVariable("attachment") int id) {
        Attachment attachment = LectureRepo.getAttachment(id);
        if (attachment != null) {
            return new DownloadView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/index", true);
    }

    @GetMapping("/ID{lecture_id}/edit")
    public ModelAndView editLecture(@PathVariable("lecture_id") String lecture_id, ModelMap model, @PathVariable String course_id) {
        model.addAttribute("lectureInfo", LectureRepo.findLecture(Integer.parseInt(lecture_id)));
        return new ModelAndView("lectureEdit", "lectureForm", new Form());
    }

    @PostMapping("/ID{lecture_id}/edit")
    public ModelAndView editLectureHandle(Form form, Model model, @PathVariable("course_id") String course_id, @PathVariable("lecture_id") String lecture_id) {
        LectureRepo.editLecture(form.getLecture_num(), form.getTitle(), Integer.parseInt(lecture_id));
        LectureRepo.addAttachment(form.getAttachments(), Integer.parseInt(lecture_id));
        model.addAttribute("lectureInfo", LectureRepo.findLecture(Integer.parseInt(lecture_id)));
        return new ModelAndView("lectureEdit", "lectureForm", new Form());
    }

    @GetMapping("/ID{lecture_id}/delete/attachment/{attachment_id}")
    public View deleteAttachment(@PathVariable("attachment_id") String attachment_id, @PathVariable("lecture_id") String lecture_id, @PathVariable String course_id) {
        LectureRepo.deleteAttachment(Integer.parseInt(attachment_id));
        return new RedirectView("/course/" + course_id + "/ID" + lecture_id + "/edit", true);
    }

    @GetMapping("/delete/ID{lecture_id}")
    public View deleteLecture(@PathVariable("lecture_id") String lecture_id) {
        LectureRepo.deleteLecture(Integer.parseInt(lecture_id));
        return new RedirectView("/index?successful", true);
    }
    @GetMapping("/addPoll")
    public ModelAndView addPoll(@PathVariable String course_id) {
        return new ModelAndView("pollAdd", "Poll", new pollForm());
    }

    @PostMapping("/addPoll")
    public ModelAndView addPollHandle(@PathVariable String course_id, pollForm pollForm) {
        Poll p = new Poll(pollForm.getQuestion(), course_id.toUpperCase(), pollForm.getAns_a(), pollForm.getAns_b(), pollForm.getAns_c(), pollForm.getAns_d());
        PollRepo.addPoll(p);
        return new ModelAndView("redirect:/index?addSuccessful");
    }

    @GetMapping("/ID{lecture_id}/delete/lectureComment/{comment_id}")
    public View deleteLectureComment(@PathVariable("comment_id") String comment_id, @PathVariable String course_id, @PathVariable String lecture_id) {
        LectureRepo.deleteComment(Integer.parseInt(comment_id));
        return new RedirectView("/course/"+course_id+"/ID"+lecture_id, true);
    }

    public static class commentForm {

        private String user_name;
        private String comment;
        private List<MultipartFile> attachments;

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

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

    }

    @GetMapping("/ID{lecture_id}/comment/add")
    public ModelAndView addLectureComment() {
        return new ModelAndView("LectureCommentAdd", "commentForm", new commentForm());
    }

    @PostMapping("/ID{lecture_id}/comment/add")
    public ModelAndView addLectureCommentHandle(commentForm form, Model model,
                                                @PathVariable("lecture_id") String lecture_id, Authentication auth, @PathVariable String course_id) {
        LectureComment lc = new LectureComment(Integer.parseInt(lecture_id), form.getComment(), auth.getName());
        LectureRepo.addLectureComment(lc);
        return new ModelAndView("redirect:/course/"+course_id+"/ID"+lecture_id);
    }

}

