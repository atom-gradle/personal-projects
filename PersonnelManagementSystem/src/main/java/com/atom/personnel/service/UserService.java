package com.atom.personnel.service;

import com.atom.personnel.exception.AuthFailureException;
import com.atom.personnel.exception.UserAlreadyExistsException;
import com.atom.personnel.exception.UserNotExistsException;
import com.atom.personnel.entity.User;
import com.atom.personnel.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepositor) {
        this.userRepository = userRepositor;
    }

    public void registerUser(@NotNull User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User "+ user.getUsername() +"already exists!");
        }
        userRepository.save(user);
    }

    public Long loginUser(@NotNull User user) {
        String username = user.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) {
            System.out.println("User Not Exists");
            throw new UserNotExistsException("User "+ username +"does not exists!");
        }
        User userToSave = optionalUser.get();
        String realPassword = userToSave.getPassword();
        String userPassword = user.getPassword();
        if(!Objects.equals(userPassword, realPassword)) {
            throw new AuthFailureException("Wrong Password for user " + username);
        }
        userToSave.setHasLogined(1);
        userRepository.save(userToSave);
        return userToSave.getId();
    }

    public void logout(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setHasLogined(0);
            userRepository.save(user);
        }
    }
}
