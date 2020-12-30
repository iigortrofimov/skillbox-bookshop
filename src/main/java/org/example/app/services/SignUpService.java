package org.example.app.services;

import org.example.app.dao.UserRepository;
import org.example.app.entity.User;
import org.example.web.dto.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private UserRepository userRepository;

    @Autowired
    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveNewUser(SignUpForm signUpForm) {
        userRepository.store(mapUser(signUpForm));
    }

    private User mapUser(SignUpForm form) {
        User user = new User();
        user.setId(user.hashCode());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        return user;
    }

}
