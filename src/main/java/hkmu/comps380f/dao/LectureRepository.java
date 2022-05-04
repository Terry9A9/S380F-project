package hkmu.comps380f.dao;

import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Lecture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectureRepository {
    public void addLecture(Lecture lecture,List<MultipartFile> attachments) ;

    public List<Lecture> findLecture(int lecture_id);

    public void deleteAttachment(int attachment_id);

    public void addAttachment(List<MultipartFile> attachments,int lecture_id);

    public void editLecture(String lecture_num, String title, int oldLecture_id);

    public Attachment getAttachment(int id);

    public List<Lecture> findAll();

    public void deleteLecture(int lecture_id);
}
