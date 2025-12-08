package com.atom.personnel.repository;

import com.atom.personnel.entity.User;
import com.atom.personnel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query(value = "select id from user where username = ?1",nativeQuery = true)
    Integer findIdByName(String username);

}
