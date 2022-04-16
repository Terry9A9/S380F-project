package hkmu.comps380f.model;

import hkmu.comps380f.model.Attachment;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Lecture implements Serializable {
    private int id;
    private String title;
    private final Map<String, Attachment> attachments = new HashMap<>();
    private String course_id;

    public Lecture(){}

    public Lecture(int id, String title, String course_id){
        this.id = id;
        this.title = title;
        this.course_id = course_id;
    }

    public int getNumberOfAttachments() {
        return this.attachments.size();
    }

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

    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }

    public void setAttachments(Attachment attachment) {
        this.attachments.put(attachment.getName(), attachment);
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
