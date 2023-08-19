package com.edi.tagsBackend.service;


import com.edi.tagsBackend.models.User;

public interface UserService {
    void save(User user);

    User findByEmail(String email);
}
