package com.aak1247.repositories;

import com.aak1247.models.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author aak12 on 2017/5/10.
 */
public interface ResourceRepository extends MongoRepository<Resource, String> {
    Resource findOneById(String id);
}
