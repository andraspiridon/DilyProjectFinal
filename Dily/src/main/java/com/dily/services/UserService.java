package com.dily.services;

import com.dily.entities.User;
import com.dily.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by Andra on 4/13/2017.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository dao;

    public Page<User> findPaginated(int page, int size) {
        return dao.findAll(new PageRequest(page, size));
    }
}