package com.springboot.rest.api.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.springboot.rest.api.entity.User;


public interface UserRepository extends MongoRepository<User, ObjectId> {

    User  findByUserName(String userName);

    void deleteByUserName(String name);
}
