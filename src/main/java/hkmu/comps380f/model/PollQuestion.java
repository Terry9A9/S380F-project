package hkmu.comps380f.model;

import java.io.Serializable;

public class PollQuestion implements Serializable {
    private String course_code;
    private String poll_id;
    private String poll_question;

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code.toUpperCase();
    }

    public String getPoll_id() {
        return poll_id;
    }

    public void setpoll_id(String poll_id) {
        this.poll_id = poll_id;
    }

    public String getPoll_question() {
        return poll_question;
    }

    public void setPoll_question(String poll_question) {
        this.poll_question = poll_question;
    }
}