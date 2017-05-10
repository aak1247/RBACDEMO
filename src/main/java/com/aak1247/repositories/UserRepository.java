package com.aak1247.repositories;

import com.aak1247.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author  aak12 on 2017/5/10.
 */
public interface UserRepository extends MongoRepository<User, String>{
    User findOneByUsername(String username);
}
