package com.icesi.edu.users.repository;

import com.icesi.edu.users.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {


}
