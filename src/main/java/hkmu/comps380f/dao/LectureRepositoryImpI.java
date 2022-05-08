package hkmu.comps380f.dao;

import hkmu.comps380f.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LectureRepositoryImpI implements LectureRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public LectureRepositoryImpI(DataSource dataSource) {
        this.jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class LectureCommentExtractor implements ResultSetExtractor<List<LectureComment>> {

        @Override
        public List<LectureComment> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, LectureComment> map = new HashMap<>();
            while (rs.next()) {
                Integer id = rs.getInt("comments_id");
                LectureComment Lecture = map.get(id);
                if (Lecture == null) {
                    Lecture = new LectureComment();
                    Lecture.setComment(rs.getString("comments"));
                    Lecture.setCommentId(id);
                    Lecture.setUser_name(rs.getString("user_name"));
                    map.put(id, Lecture);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    private static final class LectureExtractor implements ResultSetExtractor<List<Lecture>> {

        @Override
        public List<Lecture> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, Lecture> map = new HashMap<>();
            while (rs.next()) {
                Integer id = rs.getInt("lecture_id");
                Lecture Lecture = map.get(id);
                if (Lecture == null) {
                    Lecture = new Lecture();
                    Lecture.setId(id);
                    Lecture.setLecture_num(rs.getString("lecture_num"));
                    Lecture.setTitle(rs.getString("title"));
                    Lecture.setCourse_code(rs.getString("course_code"));
                    map.put(id, Lecture);
                }
                String filename = rs.getString("filename");
                if (filename != null) {
                    Attachment attachment = new Attachment();
                    attachment.setId(rs.getInt("attachment_id"));
                    attachment.setName(rs.getString("filename"));
                    attachment.setMimeContentType(rs.getString("content_type"));
                    attachment.setLecture_Id(rs.getInt("lecture_id"));
                    Lecture.setAttachments(attachment);
                }

            }
            return new ArrayList<>(map.values());
        }
    }

    private static final String SQL_INSERT_COMMENT
            = "insert into LECTURE_COMMENTS (COMMENTS, LECTURE_ID, USER_NAME) values (?, ?, ?)";


    @Override
    @Transactional
    public void addLectureComment(final LectureComment comment) {
        jdbcOp.update(SQL_INSERT_COMMENT, comment.getComment(), comment.getLectureId(), comment.getUser_name());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LectureComment> findLectureComment(int comment_id) {
        final String SQL_SELECT_Comment_BY_ID
                = "select * from LECTURE_COMMENTS where LECTURE_ID = ?";
        return jdbcOp.query(SQL_SELECT_Comment_BY_ID, new LectureCommentExtractor(), comment_id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findLecture(int lecture_id) {
        final String SQL_SELECT_Lecture_BY_ID
                = "select l.*, a.* from LECTURE_INFO as l left join COURSE_MATERIAL a on l.LECTURE_ID = a.LECTURE_ID where l.LECTURE_ID = ?";
        return jdbcOp.query(SQL_SELECT_Lecture_BY_ID, new LectureExtractor(), lecture_id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAll() {
        final String SQL_SELECT_Lecture
                = "select l.*, c.* from LECTURE_INFO as l left join COURSE_MATERIAL c on l.LECTURE_ID = c.LECTURE_ID order by LECTURE_NUM";
        return jdbcOp.query(SQL_SELECT_Lecture, new LectureExtractor());
    }

    @Override
    @Transactional
    public void deleteLecture(int lecture_id) {
        final String SQL_DELETE_USER = "delete from LECTURE_INFO where lecture_id=?";
        jdbcOp.update(SQL_DELETE_USER, lecture_id);
    }

    private static final String SQL_INSERT_lecture
            = "insert into LECTURE_INFO (COURSE_CODE, LECTURE_NUM, TITLE ) values (?, ?, ?)";

    @Override
    @Transactional
    public void addLecture(final Lecture lecture, List<MultipartFile> attachments) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_lecture,
                        new String[]{"id"});
                ps.setString(1, lecture.getCourse_code());
                ps.setString(2, lecture.getLecture_num());
                ps.setString(3, lecture.getTitle());
                return ps;
            }
        }, keyHolder);

        int lecture_id = keyHolder.getKey().intValue();
        for (MultipartFile filePart : attachments) {
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                try {
                    jdbcOp.update(SQL_INSERT_ATTACHMENT,
                            new Object[]{filePart.getOriginalFilename(),
                                    filePart.getContentType(),
                                    new SqlLobValue(filePart.getInputStream(),
                                            (int) filePart.getSize()),
                                    lecture_id},
                            new int[]{Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.INTEGER}
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static final String SQL_UPDATE_LECTURE
            = "update LECTURE_INFO set LECTURE_NUM=?, TITLE=? where LECTURE_ID=?";

    @Override
    public void editLecture(String lecture_num, String title, int oldLecture_id) {
        jdbcOp.update(SQL_UPDATE_LECTURE, lecture_num, title, oldLecture_id);
    }

    public static final String SQL_INSERT_ATTACHMENT
            = "insert into COURSE_MATERIAL (filename,content_type,content,LECTURE_ID) values (?,?,?,?)";

    @Override
    public void addAttachment(List<MultipartFile> attachments, int lecture_id) {
        for (MultipartFile filePart : attachments) {
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                try {
                    jdbcOp.update(SQL_INSERT_ATTACHMENT,
                            new Object[]{filePart.getOriginalFilename(),
                                    filePart.getContentType(),
                                    new SqlLobValue(filePart.getInputStream(),
                                            (int) filePart.getSize()),
                                    lecture_id},
                            new int[]{Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.INTEGER}
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    @Transactional
    public Attachment getAttachment(int id) {
        final String SQL_SELECT_ATTACHMENT = "select * from course_material where attachment_id=?";
        return jdbcOp.queryForObject(SQL_SELECT_ATTACHMENT, new AttachmentRowMapper(), id);
    }

    @Override
    public void deleteAttachment(int attachment_id) {
        final String SQL_DELETE_ATTACHMENT
                = "delete from COURSE_MATERIAL where attachment_id=?";
        jdbcOp.update(SQL_DELETE_ATTACHMENT, attachment_id);
    }

    @Override
    public void deleteComment(int comment_id) {
        final String SQL_DELETE_Comment
                = "delete from LECTURE_COMMENTS where COMMENTS_ID=?";
        jdbcOp.update(SQL_DELETE_Comment, comment_id);
    }

    @Override
    public List<LectureComment> findH(String name) {
        return jdbcOp.query("select c.*, l.COURSE_CODE, l.TITLE, l.LECTURE_NUM " +
                "from LECTURE_COMMENTS c, LECTURE_INFO l where c.LECTURE_ID = l.LECTURE_ID and USER_NAME = ? " +
                "order by COURSE_CODE, LECTURE_NUM", new HExtractor(), name);
    }

    private static final class HExtractor implements ResultSetExtractor<List<LectureComment>> {

        @Override
        public List<LectureComment> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, LectureComment> map = new HashMap<>();
            while (rs.next()) {
                Integer id = rs.getInt("comments_id");
                LectureComment lc = map.get(id);
                if (lc == null) {
                    lc = new LectureComment();
                    lc.setLectureId(rs.getInt("lecture_id"));
                    lc.setUser_name(rs.getString("User_name"));
                    lc.setComment(rs.getString("comments"));
                    lc.setTitle(rs.getString("title"));
                    lc.setCourse_code(rs.getString("course_code"));
                    lc.setLecture_num(rs.getString("lecture_num"));
                    map.put(id, lc);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    public List<PollComment> findPH(String name) {
        return jdbcOp.query("select * from POLL_COMMENTS  where USER_NAME = ? order by POLL_ID", new pHExtractor(), name);
    }



    private static final class pHExtractor implements ResultSetExtractor<List<PollComment>> {

        @Override
        public List<PollComment> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, PollComment> map = new HashMap<>();
            while (rs.next()) {
                Integer id = rs.getInt("poll_cid");
                PollComment pc = map.get(id);
                if (pc == null) {
                    pc = new PollComment();
                    pc.setPoll_cId(id);
                    pc.setUser_name(rs.getString("User_name"));
                    pc.setComment(rs.getString("comments"));
                    pc.setPoll_id(rs.getInt("poll_id"));
                    map.put(id, pc);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    private static final class AttachmentRowMapper implements RowMapper<Attachment> {

        @Override
        public Attachment mapRow(ResultSet rs, int i) throws SQLException {
            Attachment entry = new Attachment();
            entry.setName(rs.getString("filename"));
            entry.setMimeContentType(rs.getString("content_type"));
            Blob blob = rs.getBlob("content");
            byte[] bytes = blob.getBytes(1l, (int) blob.length());
            entry.setContents(bytes);
            entry.setLecture_Id(rs.getInt("lecture_id"));
            return entry;
        }
    }
}