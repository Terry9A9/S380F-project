package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Lecture implements Serializable {

    private int id;
    private String lecture_num;
    private String title;
    private final Map<String, Attachment> attachments = new HashMap<>();
    private String course_code;

    public Lecture(){}

    public Lecture(String  lecture_num, String title, String course_code){
        this.lecture_num = lecture_num;
        this.title = title;
        this.course_code = course_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLecture_num() {
        return lecture_num;
    }

    public void setLecture_num(String lecture_num) {
        this.lecture_num = lecture_num;
    }

    public int getNumberOfAttachments() {
        return this.attachments.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }

    public void setAttachments(Attachment attachment) {
        this.attachments.put(attachment.getName(), attachment);
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }
}
