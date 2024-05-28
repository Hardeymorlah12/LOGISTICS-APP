package com.hardeymorlah.Logistics.Repository;

import com.hardeymorlah.Logistics.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail (String email);
    User findUserByUsername(String username);
}
