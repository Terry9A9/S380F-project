package hkmu.comps380f.dao;

import hkmu.comps380f.model.Poll;
import hkmu.comps380f.model.User_choice;

public interface PollRepository {

    public void addPoll(Poll poll);

    public void delete(int poll_id);

    public void deleteComment(int comment_id);

    public Poll findPoll(int poll_id);

    public Object findChoice_a(int poll_id);

    public int findChoice_b(int poll_id);

    public int findChoice_c(int poll_id);

    public int findChoice_d(int poll_id);

    public User_choice findUser(int poll_id, String name);
}
