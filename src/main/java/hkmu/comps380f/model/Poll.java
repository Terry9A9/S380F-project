package hkmu.comps380f.model;

import java.io.Serializable;

public class Poll implements Serializable {

    public Poll(String question, String course_code, String ans_a, String ans_b, String ans_c, String ans_d) {
        this.question = question;
        this.course_code = course_code;
        this.ans_a = ans_a;
        this.ans_b = ans_b;
        this.ans_c = ans_c;
        this.ans_d = ans_d;
    }

    private int poll_id;
    private String question;
    private String course_code;
    private String ans_a;
    private String ans_b;
    private String ans_c;
    private String ans_d;

    public Poll() {
    }

    public int getId() {
        return poll_id;
    }

    public void setPoll_id(int id) {
        this.poll_id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
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