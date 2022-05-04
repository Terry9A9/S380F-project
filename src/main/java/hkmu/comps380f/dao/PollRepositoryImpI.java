package hkmu.comps380f.dao;

import hkmu.comps380f.model.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PollRepositoryImpI implements PollRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public PollRepositoryImpI(DataSource dataSource) {
        this.jdbcOp = new JdbcTemplate(dataSource);
    }

    @Override
    public void addPoll(Poll poll) {
        final String SQL_INSERT_poll
                = "insert into poll (poll_question, course_code, ans_a, ans_b, ans_c, ans_d) values (?, ?, ?, ?, ?, ?)";
        jdbcOp.update(SQL_INSERT_poll, poll.getQuestion(),poll.getCourse_code(),poll.getAns_a(),poll.getAns_b(),poll.getAns_c(),poll.getAns_d());
    }

    @Override
    public void delete(int poll_id) {
        final String SQL_DELETE_poll
                = "delete from POLL where POLL_ID = ?";
        jdbcOp.update(SQL_DELETE_poll, poll_id);
    }
}
