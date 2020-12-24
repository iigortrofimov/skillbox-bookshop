package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.dao.UserRepository;
import org.example.app.entity.User;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        User userFromRepo = userRepository.retrieveByUserName(loginFrom.getUsername());
        return (Objects.nonNull(userFromRepo) && userFromRepo.getPassword().equals(loginFrom.getPassword()));
    }
}
