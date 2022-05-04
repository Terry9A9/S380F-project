package hkmu.comps380f.dao;

public interface PollRepository {

    public void addPoll(String question, String course_code, String ans_a,String ans_b,String ans_c,String ans_d);

}
