package com.example.springmssqlapi.service;

import com.example.springmssqlapi.entity.User;
import com.example.springmssqlapi.exception.BadRequestException;
import com.example.springmssqlapi.exception.ResourceNotFoundException;
import com.example.springmssqlapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        log.info("Attempting to save user with email: {}", user.getEmail());

        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("User with email {} already exists", user.getEmail());
            throw new BadRequestException("User with email " + user.getEmail() + " already exists");
        }

        User savedUser = userRepository.save(user);
        log.info("Successfully saved user with ID: {} and email: {}", savedUser.getId(), savedUser.getEmail());

        return savedUser;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        log.info("Found {} users", users.size());
        return users;
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        log.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("User not found with ID: {}", id);
                return new ResourceNotFoundException("User not found with id: " + id);
            });

        log.info("Found user with ID: {} and email: {}", user.getId(), user.getEmail());
        return user;
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.warn("User not found with email: {}", email);
                return new ResourceNotFoundException("User not found with email: " + email);
            });

        log.info("Found user with ID: {} and email: {}", user.getId(), user.getEmail());
        return user;
    }

    public User updateUser(Long id, User userDetails) {
        log.info("Updating user with ID: {}", id);

        User existingUser = getUser(id);

        if (!existingUser.getEmail().equals(userDetails.getEmail()) &&
            userRepository.existsByEmail(userDetails.getEmail())) {
            log.warn("Email {} is already in use by another user", userDetails.getEmail());
            throw new BadRequestException("Email " + userDetails.getEmail() + " is already in use");
        }

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setEmailVerified(userDetails.getEmailVerified());

        User updatedUser = userRepository.save(existingUser);
        log.info("Successfully updated user with ID: {}", updatedUser.getId());

        return updatedUser;
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);

        User user = getUser(id);
        userRepository.delete(user);

        log.info("Successfully deleted user with ID: {}", id);
    }
}
