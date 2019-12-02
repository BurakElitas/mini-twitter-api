package com.burak.twitter.twitterapi.repository;

import com.burak.twitter.twitterapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
 List<User> findAllByIsActiveIsTrue();
 User findUserById(Long id);
 User findByEmail(String email);
 User findByUsername(String username);

 @Query(value = "SELECT  * FROM User where id not in (select follow_id from Follow where follower_id=:userId) LIMIT 3\n",nativeQuery = true)
 List<User> adviceUser(@Param("userId") Long userId);
}
