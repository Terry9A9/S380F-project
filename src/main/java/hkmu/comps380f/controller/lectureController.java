package hkmu.comps380f.controller;

import hkmu.comps380f.dao.LectureRepository;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.view.DownloadView;
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

    public static class Form {

        private int id;
        private String title;
        private List<MultipartFile> attachments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

    @GetMapping("/lecture{lecture_id}")
    public String viewLecture(@PathVariable("lecture_id") String lecture_id, ModelMap model, @PathVariable String course_id){
        model.addAttribute("lectureInfo", LectureRepo.findLecture(Integer.parseInt(lecture_id)));
        return "lectureView";
    }


    @GetMapping("/lecture{lecture_id}/attachment/{attachment:.+}")
    public View download(@PathVariable("attachment") int id) {
        Attachment attachment = LectureRepo.getAttachment(id);
        if (attachment != null) {
            return new DownloadView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/index", true);
    }

    @GetMapping("/add")
    public ModelAndView addLecture(){
        return new ModelAndView("lectureAdd", "lectureForm", new Form());
    }

    @PostMapping("/add")
    public ModelAndView addLectureHandle(Form form, Model model,@PathVariable("course_id") String course_id) {
        boolean success = true;
        try{
            Lecture l = new Lecture(form.getId(), form.getTitle(), course_id);
            LectureRepo.addLecture(l);
            LectureRepo.addAttachment(form.getAttachments(), form.getId());
        }catch (Exception DerbySQLIntegrityConstraintViolationException){
            success = false;
        }
        if (success){
            return new ModelAndView("redirect:/index?addSuccessful");
        }else{
            model.addAttribute("error", "Lecture number already exists");
            return new ModelAndView("lectureAdd","lectureForm", new Form());
        }
    }

    @GetMapping("/edit/lecture{lecture_id}")
    public ModelAndView editLecture(@PathVariable("lecture_id") String lecture_id, ModelMap model, @PathVariable String course_id){
        model.addAttribute("lectureInfo", LectureRepo.findLecture(Integer.parseInt(lecture_id)));
        return new ModelAndView("lectureEdit", "lectureForm", new Form());
    }

    @PostMapping("/edit/lecture{lecture_id}")
    public ModelAndView editLectureHandle(Form form, Model model,@PathVariable("course_id") String course_id, @PathVariable("lecture_id") String lecture_id){
        LectureRepo.editLecture(form.getTitle(), Integer.parseInt(lecture_id));
        LectureRepo.addAttachment(form.getAttachments(), Integer.parseInt(lecture_id));
        model.addAttribute("lectureInfo", LectureRepo.findLecture(Integer.parseInt(lecture_id)));
        return new ModelAndView("lectureEdit", "lectureForm", new Form());
    }

    @GetMapping("/lecture{lecture_id}/delete/attachment/{attachment_id}")
    public View deleteAttachment(@PathVariable("attachment_id") String attachment_id, @PathVariable("lecture_id") String lecture_id) {
        LectureRepo.deleteAttachment(Integer.parseInt(attachment_id));
        return new RedirectView("/course/"+attachment_id+"/edit/lecture"+lecture_id, true);
    }
}
