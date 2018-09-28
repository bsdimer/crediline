package com.crediline.view.converter;

import com.crediline.dao.CredilineRepository;
import com.crediline.dao.UserDao;
import com.crediline.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimer on 4/6/14.
 */
@Component("userConverter")
@Scope("request")
public class UserConverter extends EntityConverter<User> {
    @Inject
    private UserDao userService;

    @Override
    protected CredilineRepository<User> getRepository() {
        return userService;
    }
}
