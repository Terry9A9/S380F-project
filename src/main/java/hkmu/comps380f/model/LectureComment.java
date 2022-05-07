package hkmu.comps380f.model;

import java.io.Serializable;

public class LectureComment implements Serializable {

    private String comment;
    private String user_name;
    private int commentId;
    private int lectureId;
    private String course_code;
    private String title;
    private String lecture_num;

    public LectureComment() {
    }

    public LectureComment(int id, String comment, String UserName) {
        this.lectureId = id;
        this.comment = comment;
        this.user_name = UserName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLecture_num() {
        return lecture_num;
    }

    public void setLecture_num(String lecture_num) {
        this.lecture_num = lecture_num;
    }
}
