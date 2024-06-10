package com.aaron.app.crud_app.service;

import com.aaron.app.crud_app.models.User;

import java.util.List;

public interface ApplicationService {

    public List<User> getUsers();
    public String addUser(User user );
    public String updateUser( Integer id, User user);
    public String deleteUser(Integer id);

}
