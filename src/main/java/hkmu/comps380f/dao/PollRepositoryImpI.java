package hkmu.comps380f.dao;

import hkmu.comps380f.model.Poll;
import hkmu.comps380f.model.PollComment;
import hkmu.comps380f.model.User_choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        jdbcOp.update(SQL_INSERT_poll, poll.getQuestion(), poll.getCourse_code(), poll.getAns_a(), poll.getAns_b(), poll.getAns_c(), poll.getAns_d());
    }

    @Override
    public void delete(int poll_id) {
        final String SQL_DELETE_poll
                = "delete from POLL where POLL_ID = ?";
        jdbcOp.update(SQL_DELETE_poll, poll_id);
    }

    @Override
    @Transactional(readOnly = true)
    public Poll findPoll(int poll_id) {
        final String SQL_FIND_Poll
                = "select * from poll where poll_id = ?";
        return jdbcOp.query(SQL_FIND_Poll, new PollExtractor(), poll_id);
    }

    private static final class PollExtractor implements ResultSetExtractor<Poll> {
        @Override
        public Poll extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Poll poll = new Poll();
            rs.next();
            poll.setPoll_id(rs.getInt("poll_id"));
            poll.setCourse_code(rs.getString("course_code"));
            poll.setQuestion(rs.getString("poll_question"));
            poll.setAns_a(rs.getString("ans_a"));
            poll.setAns_b(rs.getString("ans_b"));
            poll.setAns_c(rs.getString("ans_c"));
            poll.setAns_d(rs.getString("ans_d"));
            return poll;
        }
    }

    private static final class ChoiceExtractor implements ResultSetExtractor<User_choice> {
        @Override
        public User_choice extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            if (rs.next()) {
                User_choice user_choice = new User_choice();
                user_choice.setUser_choice(rs.getString("user_choice"));
                return user_choice;
            } else {
                User_choice user_choice = new User_choice();
                user_choice.setUser_choice("");
                return user_choice;
            }
        }
    }

    private static final class ChoiceExtractor1 implements ResultSetExtractor<Object> {
        @Override
        public Object extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return null;
            }
        }
    }


    private static final class HExtractor implements ResultSetExtractor<List<User_choice>> {

        @Override
        public List<User_choice> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User_choice> map = new HashMap<>();
            while (rs.next()) {
                Integer id = rs.getInt("Choice_id");
                User_choice User_choice = map.get(id);
                if (User_choice == null) {
                    User_choice = new User_choice();
                    User_choice.setChoice_id(id);
                    User_choice.setUser_name(rs.getString("User_name"));
                    User_choice.setUser_choice(rs.getString("User_choice"));
                    User_choice.setPoll_id(rs.getInt("Poll_id"));
                    User_choice.setQuestion(rs.getString("POLL_QUESTION"));
                    User_choice.setAns_a(rs.getString("ans_a"));
                    User_choice.setAns_b(rs.getString("ans_b"));
                    User_choice.setAns_c(rs.getString("ans_c"));
                    User_choice.setAns_d(rs.getString("ans_d"));
                    User_choice.setCourse_code(rs.getString("course_code"));
                    map.put(id, User_choice);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    final String SQL_FIND_Choice
            = "select Count(*) from User_choice where poll_id = ? and user_choice =?";

    public Object findChoice_a(int poll_id) {
        return jdbcOp.query(SQL_FIND_Choice, new ChoiceExtractor1(), poll_id, "a");
    }

    public Object findChoice_b(int poll_id) {
        return jdbcOp.query(SQL_FIND_Choice, new ChoiceExtractor1(), poll_id, "b");
    }

    public Object findChoice_c(int poll_id) {
        return jdbcOp.query(SQL_FIND_Choice, new ChoiceExtractor1(), poll_id, "c");
    }

    public Object findChoice_d(int poll_id) {
        return jdbcOp.query(SQL_FIND_Choice, new ChoiceExtractor1(), poll_id, "d");
    }

    public User_choice findUser(int poll_id, String name) {
        return jdbcOp.query("select User_choice, CHOICE_ID from User_choice where user_name = ? and POLL_ID= ?", new ChoiceExtractor(), name, poll_id);
    }

    @Override
    public List<User_choice> findH(String name) {
        return jdbcOp.query("select u.*, p.* from User_choice u, poll p where u.POLL_ID = p.POLL_ID and user_name = ?", new HExtractor(), name);
    }

    @Override
    public void addUser_choice(String User_name, String User_choice, int poll_id) {
        final String SQL_INSERT_User_choice
                = "insert into User_choice (user_name, user_choice, poll_id) values (?, ?, ?)";
        jdbcOp.update(SQL_INSERT_User_choice, User_name, User_choice, poll_id);
    }

    @Override
    public void editUser_choice(String User_name, String User_choice, int poll_id) {
        final String SQL_UPDATE_User_choice
                = "update User_choice SET user_choice=? where user_name=? and poll_id=?";
        jdbcOp.update(SQL_UPDATE_User_choice, User_choice, User_name, poll_id);
    }

    private static final class PollCommentExtractor implements ResultSetExtractor<List<PollComment>> {

        @Override
        public List<PollComment> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, PollComment> map = new HashMap<>();
            while (rs.next()) {
                Integer id = rs.getInt("poll_cid");
                PollComment pollcomment = map.get(id);
                if (pollcomment == null) {
                        pollcomment = new PollComment();
                    pollcomment.setComment(rs.getString("comments"));
                    pollcomment.setPoll_cId(id);
                    pollcomment.setUser_name(rs.getString("user_name"));
                    map.put(id, pollcomment);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    private static final String SQL_INSERT_COMMENT
            = "insert into POLL_COMMENTS (COMMENTS, POLL_ID, USER_NAME) values (?, ?, ?)";

    @Override
    @Transactional
    public void addPollComment(PollComment comment) {
        jdbcOp.update(SQL_INSERT_COMMENT, comment.getComment(), comment.getPoll_cId(), comment.getUser_name());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PollComment> findPollComment(int poll_id) {
        final String SQL_SELECT_Comment_BY_ID
                = "select * from POLL_COMMENTS where poll_id = ?";
        return jdbcOp.query(SQL_SELECT_Comment_BY_ID, new PollCommentExtractor(), poll_id);
    }

    @Override
    public void deleteComment(int comment_id) {
        final String SQL_DELETE_poll
                = "delete from POLL_COMMENTS where POLL_CID = ?";
        jdbcOp.update(SQL_DELETE_poll, comment_id);
    }

}
