package hkmu.comps380f.dao;

import hkmu.comps380f.model.Student;

import java.util.List;

public interface StudentRepository {

    public void addEntry(Student e);

    public List<Student> listEntries();
}
