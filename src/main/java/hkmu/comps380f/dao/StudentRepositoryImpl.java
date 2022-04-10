package hkmu.comps380f.dao;

import hkmu.comps380f.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class StudentRepositoryImpl implements StudentRepository{

    private final JdbcOperations jdbcOp;

    @Autowired
    public StudentRepositoryImpl(DataSource dataSource) {
        this.jdbcOp = new JdbcTemplate(dataSource);
    }

//    private static final class EntryRowMapper implements RowMapper<Student> {
//        @Override
//        public Student mapRow(ResultSet rs, int i) throws SQLException {
//            Student entry = new Student();
//            entry.setId(rs.getInt("id"));
//            entry.setName(rs.getString("name"));
//            entry.setMessage(rs.getString("message"));
//            entry.setDate(toDate(rs.getTimestamp("date")));
//            return entry;
//        }
//    }

    private static final String SQL_INSERT_student
            = "insert into users (username, password, fullName, phoneNumber, address) values (?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_role
            = "insert into USER_ROLES (username, ROLE) values (?, ?)";

    @Override
    public void addEntry(Student e) {

            jdbcOp.update(SQL_INSERT_student,
                    e.getUsername(),
                    e.getPassword(),
                    e.getFullName(),
                    e.getPhoneNumber(),
                    e.getAddress()
            );
            jdbcOp.update(SQL_INSERT_role,
                    e.getUsername(),
                    e.getRole()
            );
    }

    @Override
    public List<Student> listEntries() {
        return null;
    }
}
