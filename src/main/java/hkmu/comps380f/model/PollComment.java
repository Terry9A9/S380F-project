package hkmu.comps380f.model;

import java.io.Serializable;

public class PollComment implements Serializable {

    private String comment;
    private String user_name;
    private int poll_cId;

    public PollComment() {
    }

    public PollComment(int id, String comment, String UserName) {
        this.poll_cId = id;
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

    public int getPoll_cId() {
        return poll_cId;
    }

    public void setPoll_cId(int poll_cId) {
        this.poll_cId = poll_cId;
    }

}
