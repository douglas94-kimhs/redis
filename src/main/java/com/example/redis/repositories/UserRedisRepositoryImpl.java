package com.example.redis.repositories;

import com.example.redis.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Primary
public class UserRedisRepositoryImpl implements UserRepository {
    private RedisTemplate<String, User> redisTemplate;

    @Value("${run.env}")
    private String env;

    @Value("${run.name}")
    private String name;

    @Value("${run.profile}")
    private String qq;

    private HashOperations hashOperations;

    public UserRedisRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(User user) {
        hashOperations.put(KEY, user.getId(), user);
    }

    @Override
    public Map<String, User> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public User findById(String id) {
        return (User)hashOperations.get(KEY, id);
    }

    @Override
    public void update(User user) {
        save(user);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }
}
