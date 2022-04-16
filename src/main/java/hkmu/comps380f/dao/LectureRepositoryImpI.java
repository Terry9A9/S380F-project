package hkmu.comps380f.dao;

import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

    private static final class LectureExtractor implements ResultSetExtractor<List<Lecture>> {

        @Override
        public List<Lecture> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, Lecture> map = new HashMap<>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Lecture Lecture = map.get(id);
                if (Lecture == null) {
                    Lecture = new Lecture();
                    Lecture.setId(id);
                    Lecture.setTitle(rs.getString("title"));
                    Lecture.setCourse_id(rs.getString("course_id"));
                    map.put(id, Lecture);
                }
                String filename = rs.getString("filename");
                if (filename != null) {
                    Attachment attachment = new Attachment();
                    attachment.setId(rs.getInt(4));
                    attachment.setName(rs.getString("filename"));
                    attachment.setMimeContentType(rs.getString("content_type"));
                    attachment.setLecture_Id(rs.getInt("lecture_id"));
                    Lecture.setAttachments(attachment);
                }

            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findLecture(int lecture_id) {
        final String SQL_SELECT_Lecture_BY_ID
                = "select l.*, a.* from LECTURE as l left join COURSE_MATERIAL a on l.id = a.LECTURE_ID where l.id = ?";
        return jdbcOp.query(SQL_SELECT_Lecture_BY_ID, new LectureExtractor(),lecture_id);
    }

    private static final String SQL_INSERT_lecture
            = "insert into lecture values (?, ?, ?)";

    private static final String SQL_INSERT_attachment
            = "insert into COURSE_MATERIAL (filename, CONTENT_TYPE, content , LECTURE_ID) values (?, ?, ?, ?)";

    @Override
    @Transactional
    public void addLecture(Lecture lecture) {
        jdbcOp.update(SQL_INSERT_lecture,
                lecture.getId(),
                lecture.getTitle(),
                lecture.getCourse_id()
        );
    }

    public static final String SQL_UPDATE_LECTURE
            = "update LECTURE set  TITLE=? where id=?";
    @Override
    public void editLecture(String title, int oldLecture_id){
        jdbcOp.update(SQL_UPDATE_LECTURE, title,oldLecture_id);
    }

    public static final String SQL_INSERT_ATTACHMENT
            = "insert into COURSE_MATERIAL (filename,content_type,  content,LECTURE_ID) values (?,?,?,?)";

    @Override
    public void addAttachment(List<MultipartFile> attachments,int lecture_id){
        for (MultipartFile filePart : attachments) {
            System.out.println(filePart.getOriginalFilename()+filePart.getSize());
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                try {
                    System.out.println(filePart.getInputStream());
                    jdbcOp.update(SQL_INSERT_ATTACHMENT,
                            new Object[]{filePart.getOriginalFilename(),
                                    filePart.getContentType(),
                                    new SqlLobValue(filePart.getInputStream(),
                                            (int)filePart.getSize()),
                                    lecture_id},
                            new int[]{Types.VARCHAR, Types.VARCHAR,Types.BLOB, Types.INTEGER}
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
        final String SQL_SELECT_ATTACHMENT = "select * from course_material where id=?";
        return jdbcOp.queryForObject(SQL_SELECT_ATTACHMENT, new AttachmentRowMapper(), id);
    }

    @Override
    public void deleteAttachment(int attachment_id) {
        final String SQL_DELETE_ATTACHMENT
                = "delete from COURSE_MATERIAL where id=?";
        jdbcOp.update(SQL_DELETE_ATTACHMENT, attachment_id);
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