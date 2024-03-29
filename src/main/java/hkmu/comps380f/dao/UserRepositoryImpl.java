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

    private static final String SQL_INSERT_student
            = "insert into USER_INFO values (?, ?, ?, ?, ?,?)";

    @Override
    @Transactional
    public void addUser(WebUser WebUser) {
        jdbcOp.update(SQL_INSERT_student,
                WebUser.getUsername(),
                WebUser.getPassword(),
                WebUser.getFullName(),
                WebUser.getPhoneNumber(),
                WebUser.getAddress(),
                WebUser.getRole()
        );
    }

    private static final String SQL_UPDATE_student
            = "update USER_INFO SET USER_NAME=?, USER_pw=?, user_fullname=?, user_phone=?, user_address=?, user_type=? WHERE USER_NAME=?";

    @Override
    @Transactional
    public void updateUser(WebUser WebUser, String username) {
        jdbcOp.update(SQL_UPDATE_student,
                WebUser.getUsername(),
                WebUser.getPassword(),
                WebUser.getFullName(),
                WebUser.getPhoneNumber(),
                WebUser.getAddress(),
                WebUser.getRole(),
                username
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebUser> findAll() {
        final String SQL_SELECT_USERS
                = "select * from USER_INFO order by USER_NAME";
        return jdbcOp.query(SQL_SELECT_USERS, new UserExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebUser> findUser(String username) {
        final String SQL_SELECT_USERS_BY_ID
                = "select * from USER_INFO where USER_NAME = ?";
        return jdbcOp.query(SQL_SELECT_USERS_BY_ID, new UserExtractor(), username);
    }

    private static final class UserExtractor implements ResultSetExtractor<List<WebUser>> {
        @Override
        public List<WebUser> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, WebUser> map = new HashMap<>();
            while (rs.next()) {
                String username = rs.getString("user_name");
                WebUser user = map.get(username);
                if (user == null) {
                    user = new WebUser();
                    user.setUsername(username);
                    user.setPasswordFromDB(rs.getString("user_pw").substring(6));
                    user.setFullName(rs.getString("user_fullname"));
                    user.setPhoneNumber(rs.getString("user_phone"));
                    user.setAddress(rs.getString("user_address"));
                    user.setRole(rs.getString("user_type"));
                    map.put(username, user);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public void delete(String username) {
        final String SQL_DELETE_USER = "delete from USER_INFO where USER_NAME=?";
        jdbcOp.update(SQL_DELETE_USER, username);
    }

}
