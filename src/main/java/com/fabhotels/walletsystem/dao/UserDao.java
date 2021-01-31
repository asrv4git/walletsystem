package com.fabhotels.walletsystem.dao;

import com.fabhotels.walletsystem.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String userName);

    Optional<User> findUserByMobileNumber(String mobileNumber);

    Optional<User> findUserByUsernameAndPassword(String userName, String Password);
}
