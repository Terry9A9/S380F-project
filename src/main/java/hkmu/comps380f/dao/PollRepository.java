package hkmu.comps380f.dao;

import hkmu.comps380f.model.Poll;

public interface PollRepository {

    public void addPoll(Poll poll);

    public void delete(int poll_id);

}
