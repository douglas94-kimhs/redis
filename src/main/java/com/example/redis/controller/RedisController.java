package com.example.redis.controller;

import com.example.redis.model.User;
import com.example.redis.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@NoArgsConstructor

public class RedisController {
    private UserRepository userRepository;

    @Autowired
    public RedisController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/add/{id}/{firstName}/{lastName}")
    public User add(@PathVariable("firstName") final String firstName,
                    @PathVariable("id") final String id, @PathVariable("lastName") final String lastName) {
        userRepository.save(new User(id, firstName, lastName));
        return userRepository.findById(id);
    }

    @GetMapping("/query/{id}")
    public User find(@PathVariable("id") final String id) {
        return userRepository.findById(id);
    }

    @GetMapping("/all")
    public Map<String, User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/delete/{id}")
    public Map<String, User> delete(@PathVariable("id") final String id) {
        userRepository.delete(id);
        return all();
    }

    @GetMapping("/update/{id}/{firstName}/{lastName}")
    public User update(@PathVariable("firstName") final String firstName,
                       @PathVariable("id") final String id, @PathVariable("lastName") final String lastName) {
        userRepository.update(new User(id, firstName, lastName));
        return userRepository.findById(id);
    }
}
