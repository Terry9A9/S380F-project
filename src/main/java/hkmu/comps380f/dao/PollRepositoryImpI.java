package hkmu.comps380f.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class PollRepositoryImpI implements PollRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public PollRepositoryImpI(DataSource dataSource) {
        this.jdbcOp = new JdbcTemplate(dataSource);
    }

    @Override
    public void addPoll(String question, String course_code, String ans_a, String ans_b, String ans_c, String ans_d) {
        final String SQL_INSERT_poll
                = "insert into poll (poll_question, course_code, ans_a, ans_b, ans_c, ans_d) values (?, ?, ?, ?, ?, ?)";
        jdbcOp.update(SQL_INSERT_poll, question,course_code,ans_a,ans_b,ans_c,ans_d);
    }
}
