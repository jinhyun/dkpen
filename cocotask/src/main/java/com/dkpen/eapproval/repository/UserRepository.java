package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
