package com.humanResourceProject.service;


import com.humanResourceProject.model.User;
import com.humanResourceProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(int pageNo, int pageSize) {
        log.info("Retrieving all users");

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return userRepository.findAll(pageable).getContent();
    }

    public User getUserById(Long userId) {
        log.info("Retrieving user by ID: {}", userId);

        return userRepository.getById(userId);
    }

    public User getUserByEmail(String email) {
        log.info("Retrieving user by email: {}", email);

        return userRepository.getUserByEmail(email);
    }

    public void addUser(User user) {
        userRepository.save(user);
        log.info("User added: {}", user);
    }

    public void deleteUser(Long userId) {
        log.info("Deleting user with ID: {}", userId);

        userRepository.deleteById(userId);
    }

    public void updateUser(User user) {
        User existingUser = userRepository.getUserByEmail(user.getEmail());

        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPassword(user.getPassword());

            userRepository.save(existingUser);
            log.info("User updated: {}", existingUser);
        } else {
            log.info("User with email {} not found", user.getEmail());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("user"))
        );
    }
}