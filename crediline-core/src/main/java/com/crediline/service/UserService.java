package com.crediline.service;

import com.crediline.dao.UserDao;
import com.crediline.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 2/21/14.
 */

/**
 * This class should be changed to use AoP to update the accessible properties of the entities
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("userService")
public class UserService extends AbstractService<User> {
    @Inject
    private UserDao repository;

    public UserDao getRepository() {
        return repository;
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
