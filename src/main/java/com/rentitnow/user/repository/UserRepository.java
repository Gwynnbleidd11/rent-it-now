package com.rentitnow.user.repository;

import com.rentitnow.user.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    @Override
    User save(User user);

    List<User> findAll();
}
