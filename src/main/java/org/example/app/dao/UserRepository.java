package org.example.app.dao;

import org.apache.log4j.Logger;
import org.example.app.entity.User;
import org.example.app.services.IdProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements ProjectRepository<User>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(UserRepository.class);
    private List<User> userRepo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<User> retrieveAll() {
        return new ArrayList<>(userRepo);
    }

    @Override
    public void store(User user) {
        logger.info("store new user: " + user);
        if (!userRepo.contains(user)) {
            user.setId(context.getBean(IdProvider.class).provideId(user));
            userRepo.add(user);
        }
        logger.info("User with this name: " + user.getName() + " already exists!");
    }

    @Override
    public boolean removeItemById(String userId) {
        for (User user : retrieveAll()) {
            if (user.getId().equals(userId)) {
                logger.info("remove user completed: " + user);
                return userRepo.remove(user);
            }
        }
        return false;
    }

    public User retrieveByUserName(String userName) {
        for (User user : retrieveAll()) {
            if (user.getName().equals(userName)) {
                logger.info("returned user: " + user);
                return user;
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
