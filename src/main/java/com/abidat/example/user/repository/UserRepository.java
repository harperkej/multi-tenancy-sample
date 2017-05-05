package com.abidat.example.user.repository;

import com.abidat.example.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by a.kuci on 4/27/2017.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {


}
