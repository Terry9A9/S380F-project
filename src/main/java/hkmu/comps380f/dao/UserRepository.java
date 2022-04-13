package hkmu.comps380f.dao;

import hkmu.comps380f.model.WebUser;

import java.util.List;

public interface UserRepository {

    public void addUser(WebUser WebUser);

    public List<WebUser> findAll();

    public List<WebUser> findUser(String username);

    public void delete(String username);
}

