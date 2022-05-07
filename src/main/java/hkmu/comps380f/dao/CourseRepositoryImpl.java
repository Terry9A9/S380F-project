package hkmu.comps380f.dao;

import hkmu.comps380f.model.Course;
import hkmu.comps380f.model.PollQuestion;
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
public class CourseRepositoryImpl implements CourseRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public CourseRepositoryImpl(DataSource dataSource) {
        this.jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class CourseExtractor implements ResultSetExtractor<List<Course>> {
        @Override
        public List<Course> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, Course> map = new HashMap<>();
            while (rs.next()) {
                String course_code = rs.getString("course_code");
                Course course = map.get(course_code);
                if (course == null) {
                    course = new Course();
                    course.setCourse_code(course_code);
                    course.setCourse_name(rs.getString("course_name"));
                    map.put(course_code, course);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        final String SQL_SELECT_Course
                = "select * from COURSE_INFO";
        return jdbcOp.query(SQL_SELECT_Course, new CourseRepositoryImpl.CourseExtractor());
    }

    @Override
    public void addCourse(String course_code, String course_name) {
        final String SQL_INSERT_student
                = "insert into COURSE_INFO values (?, ?)";
        jdbcOp.update(SQL_INSERT_student, course_code.toUpperCase(), course_name);
    }
    private static final class PollQuestionExtractor implements ResultSetExtractor<List<PollQuestion>> {
        @Override
        public List<PollQuestion> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, PollQuestion> map = new HashMap<>();
            while (rs.next()) {
                String course_code = rs.getString("course_code");
                String poll_id = rs.getString("poll_id");
                String poll_question = rs.getString("poll_question");
                PollQuestion pollQuestion = map.get(course_code);
                if (pollQuestion == null) {
                    pollQuestion = new PollQuestion();
                    pollQuestion.setCourse_code(course_code);
                    pollQuestion.setpoll_id(poll_id);
                    pollQuestion.setPoll_question(poll_question);
                    map.put(course_code, pollQuestion);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PollQuestion> findPollQuestion() {
        final String SQL_SELECT_PollQuestion = "SELECT poll_id, poll_question, course_code FROM poll";
        return jdbcOp.query(SQL_SELECT_PollQuestion, new CourseRepositoryImpl.PollQuestionExtractor());

    }
}
