package hkmu.comps380f.dao;

import hkmu.comps380f.model.Course;
import hkmu.comps380f.model.WebUser;
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
import java.util.*;

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
}
