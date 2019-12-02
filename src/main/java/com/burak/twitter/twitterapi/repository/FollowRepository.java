package com.burak.twitter.twitterapi.repository;

import com.burak.twitter.twitterapi.entity.Follow;
import com.burak.twitter.twitterapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByFollowId(Long followId);
    List<Follow> findAllByFollowerId(Long followerId);
    Follow findByFollowerIdAndFollowId(Long followerId,Long followId);
}
