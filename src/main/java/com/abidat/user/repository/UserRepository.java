package com.abidat.user.repository;

import com.abidat.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by a.kuci on 4/27/2017.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {


}
