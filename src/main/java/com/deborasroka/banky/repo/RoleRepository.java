package com.deborasroka.banky.repo;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.deborasroka.banky.model.Role;

public interface RoleRepository  extends MongoRepository<Role, String>{

}
