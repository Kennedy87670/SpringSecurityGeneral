package com.generaSrpinglSecurity.spring.repository;

import com.generaSrpinglSecurity.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User, Long>{

    Optional<User> findByUserName(String userName);
}
