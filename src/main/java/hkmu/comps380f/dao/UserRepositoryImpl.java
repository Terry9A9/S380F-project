package hkmu.comps380f.dao;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcOp = new JdbcTemplate(dataSource);
    }

    //    private static final class UserRowMapper implements RowMapper<Users> {
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
    public void addUser(WebUser WebUser) {

        jdbcOp.update(SQL_INSERT_student,
                WebUser.getUsername(),
                WebUser.getPassword(),
                WebUser.getFullName(),
                WebUser.getPhoneNumber(),
                WebUser.getAddress()
        );
        for (String role : WebUser.getRoles()) {
            jdbcOp.update(SQL_INSERT_role,
                    WebUser.getUsername(),
                    role
            );
        }

    }


    @Override
    @Transactional(readOnly = true)
    public List<WebUser> findAll() {
        final String SQL_SELECT_USERS
                = "select users.*, user_roles.role from users, user_roles where users.username = user_roles.username";
        return jdbcOp.query(SQL_SELECT_USERS, new UserExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebUser> findUser(String username) {
        final String SQL_SELECT_USERS_BY_ID
                = "select users.*, user_roles.ROLE from users, user_roles " +
                "where users.USERNAME = user_roles.USERNAME and users.USERNAME = ?";
        return jdbcOp.query(SQL_SELECT_USERS_BY_ID, new UserExtractor(), username);
    }

    private static final class UserExtractor implements ResultSetExtractor<List<WebUser>> {
        @Override
        public List<WebUser> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, WebUser> map = new HashMap<>();
            while (rs.next()) {
                String username = rs.getString("username");
                WebUser user = map.get(username);
                if (user == null) {
                    user = new WebUser();
                    user.setUsername(username);
                    user.setPasswordFromDB(rs.getString("password"));
                    user.setFullName(rs.getString("fullname"));
                    user.setPhoneNumber(rs.getString("phonenumber"));
                    user.setAddress(rs.getString("address"));
                    map.put(username, user);
                }
                user.getRoles().add(rs.getString("role"));
                System.out.println(user.getUsername()+user.getPassword());
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public void delete(String username) {
        final String SQL_DELETE_USER = "delete from users where username=?";
        final String SQL_DELETE_ROLES = "delete from user_roles where username=?";
        jdbcOp.update(SQL_DELETE_ROLES, username);
        jdbcOp.update(SQL_DELETE_USER, username);
    }

}
