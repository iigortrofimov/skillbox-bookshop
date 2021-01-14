package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.dao.UserRepository;
import org.example.app.entity.User;
import org.example.web.dto.SignUpForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserDetailServiceImpl.class);

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.retrieveByUserName(username);
        if (user != null) {
            return user;
        } else {
            logger.info("User '" + username + "' not found");
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
    }

    public boolean removeById(Integer userID) {
        return userRepository.removeItemById(userID);
    }

    public void saveNewUser(SignUpForm signUpForm) {
        userRepository.store(mapUser(signUpForm));
    }

    private User mapUser(SignUpForm form) {
        User user = new User();
        user.setId(user.hashCode());
        user.setUsername(form.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
        return user;
    }

}
