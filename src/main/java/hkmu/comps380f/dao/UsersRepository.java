package hkmu.comps380f.dao;

import hkmu.comps380f.model.Users;

import java.util.List;

public interface UsersRepository {

    public void addEntry(Users e);

    public List<Users> listEntries();
}
