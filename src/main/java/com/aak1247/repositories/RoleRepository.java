package com.aak1247.repositories;

import com.aak1247.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author  aak12 on 2017/5/10.
 */
public interface RoleRepository extends MongoRepository<Role,String>{
    Role findOneByRoleName(String roleName);
}
