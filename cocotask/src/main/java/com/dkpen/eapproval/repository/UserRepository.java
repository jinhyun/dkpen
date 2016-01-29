package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
