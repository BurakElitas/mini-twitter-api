package com.burak.twitter.twitterapi.repository;

import com.burak.twitter.twitterapi.entity.Tweet;
import com.burak.twitter.twitterapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet,Long> {
   List<Tweet> findAllByUserIdOrderByCreatedOnDesc(Long id);
   List<Tweet> findAllByParentTweetId(Long id);

   @Query(value = "Select Distinct * from ( Select Tweet.id,Tweet.description,Tweet.comment_count,Tweet.fav_count,Tweet.reetweet_count,ReTweet.created_on,User.name,User.surname,User.username,Tweet.user_id \n" +
           "from ReTweet inner join Tweet on ReTweet.tweet_id=Tweet.id inner join User on Tweet.user_id=User.id\n" +
           "where ReTweet.user_id=:userId\n" +
           "Union All\n" +
           "Select Tweet.id,Tweet.description,Tweet.comment_count,Tweet.fav_count,Tweet.reetweet_count,Tweet.created_on,User.name,User.surname,User.username, null\n" +
           "from ReTweet inner join User on ReTweet.user_id=User.id inner join Tweet on User.id=Tweet.user_id\n" +
           "where User.id=:userId\n" +
           "order by created_on desc ) as T \n",nativeQuery = true)
   List<String[]> getAllTweetByUserId(@Param("userId") long userId);//profilindeki görünecek tweetler


   @Query(value ="\n" +
           "SELECT t.id,t.description,t.comment_count,t.reetweet_count,t.fav_count,t.created_on,t.user_id,u.name,u.surname,u.username,  null,null as asfasfa \n" +
           "FROM school.follow f inner join school.user u on f.follow_id=u.id inner join school.tweet t\n" +
           "on u.id=t.user_id where f.follower_id=:userId\n" +
           "Union all\n" +
           "SELECT t.id,t.description,t.comment_count,t.reetweet_count,t.fav_count,r.created_on,t.user_id, u.name,u.surname,u.username, us.username,us.id  \n" +
           " FROM school.follow f inner join school.retweet r on f.follow_id=r.user_id inner join school.user us on r.user_id=us.id \n" +
           "inner join school.tweet t on r.tweet_id=t.id inner join school.user u on t.user_id=u.id\n" +
           " where f.follower_id=:userId\n" +
           "order by created_On desc\n" ,nativeQuery = true)
   List<String[]> getAllFollowsTweetByUserId(@Param("userId") Long userId);

   Tweet findTweetById(Long id);

   void deleteById(Long id);

}
