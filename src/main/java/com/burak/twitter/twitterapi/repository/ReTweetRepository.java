package com.burak.twitter.twitterapi.repository;

import com.burak.twitter.twitterapi.entity.ReTweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReTweetRepository extends JpaRepository<ReTweet,Long> {
    boolean existsByUserId(Long userId);
    ReTweet findByUserIdAndTweetId(Long userId,Long tweetId);


    void deleteAllByTweetId(Long tweetId);
}
