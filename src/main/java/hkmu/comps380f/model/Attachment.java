package hkmu.comps380f.model;

import java.io.Serializable;

public class Attachment implements Serializable {

    private int id;
    private String name;
    private String mimeContentType;
    private byte[] contents;
    private int lecture_Id;

    // getters and setters of all properties
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeContentType() {
        return mimeContentType;
    }

    public void setMimeContentType(String mimeContentType) {
        this.mimeContentType = mimeContentType;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public int getLecture_Id() {
        return lecture_Id;
    }

    public void setLecture_Id(int lecture_Id) {
        this.lecture_Id = lecture_Id;
    }
}