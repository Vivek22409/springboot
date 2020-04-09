package com.advenspirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advenspirit.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
