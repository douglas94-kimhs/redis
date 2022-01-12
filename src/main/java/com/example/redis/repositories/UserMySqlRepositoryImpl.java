package com.example.redis.repositories;

import com.example.redis.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
//@Primary
@Qualifier
public class UserMySqlRepositoryImpl implements UserRepository {
    @Override
    public void save(User user) {

    }

    @Override
    public Map<String, User> findAll() {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String id) {

    }
}
