package com.crediline.dao;

import com.crediline.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("userDao")
public interface UserDao extends CredilineRepository<User> {
    User findByUsername(String username);
}