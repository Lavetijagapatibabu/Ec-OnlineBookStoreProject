package com.book.mongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.book.mongoEntity.UserRegisterMongo;

@Repository
public interface UserRegisterMongoRepo extends MongoRepository<UserRegisterMongo, String>{

}
