package com.pfm.restapi.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepo extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsername(String username);
}
