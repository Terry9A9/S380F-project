package hkmu.comps380f.dao;

import hkmu.comps380f.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcOp = new JdbcTemplate(dataSource);
    }

//    private static final class EntryRowMapper implements RowMapper<Users> {
//        @Override
//        public Users mapRow(ResultSet rs, int i) throws SQLException {
//            Users entry = new Users();
//            entry.setId(rs.getInt("id"));
//            entry.setName(rs.getString("name"));
//            entry.setMessage(rs.getString("message"));
//            entry.setDate(toDate(rs.getTimestamp("date")));
//            return entry;
//        }
//    }

    private static final String SQL_INSERT_student
            = "insert into users values (?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_role
            = "insert into USER_ROLES (username, ROLE) values (?, ?)";

    @Override
    @Transactional
    public void addEntry(Users e) {

        jdbcOp.update(SQL_INSERT_student,
                e.getUsername(),
                e.getPassword(),
                e.getFullName(),
                e.getPhoneNumber(),
                e.getAddress()
        );
        for (String role : e.getRoles()) {
            jdbcOp.update(SQL_INSERT_role,
                    e.getUsername(),
                    role
            );
        }

    }

    @Override
    public List<Users> listEntries() {
        return null;
    }
}
