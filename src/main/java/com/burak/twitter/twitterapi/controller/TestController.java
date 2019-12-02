package com.burak.twitter.twitterapi.controller;

import com.burak.twitter.twitterapi.entity.Tweet;
import com.burak.twitter.twitterapi.entity.User;
import com.burak.twitter.twitterapi.repository.UserRepository;
import com.burak.twitter.twitterapi.service.TweetService;
import com.burak.twitter.twitterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class TestController {

    private final UserService userService;
    private final TweetService tweetService;

    @Autowired
    private UserRepository userRepository;

    public TestController(UserService userService,TweetService tweetService){
        this.userService=userService;
        this.tweetService=tweetService;
    }

   /* @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseEntity<List<User>> test2(){
        User user=new User();
        user.setUsername("burakelitas");
        user.setSurname("elitaş");
        user.setName("burak");
        user.setEmail("burak.elitas@outlook.com.tr");
        user.setPassword("123456");
        user.setCreatedOn(Timestamp.valueOf("2016-11-16 06:43:19.77"));
        user.setModifiedOn(Timestamp.valueOf("2016-11-16 06:43:19.77"));
        User user2=new User();
        user2.setUsername("burakelitas2");
        user2.setSurname("elitaş");
        user2.setName("burak");
        user2.setPassword("12345");
        user2.setEmail("burak2.elitas@outlook.com.tr");
        user2.setCreatedOn(Timestamp.valueOf("2016-11-16 06:43:19.77"));
        user2.setModifiedOn(Timestamp.valueOf("2016-11-16 06:43:19.77"));
        userRepository.save(user);
        userRepository.save(user2);
        Tweet tweet=new Tweet();
        tweet.setUser(user);
        tweet.setDescription("nasilsiniz gençler");
        tweet.setCreatedOn(Timestamp.valueOf("2016-11-16 06:43:19.77"));
        Tweet tweet2=new Tweet();
        tweet2.setUser(user2);
        tweet2.setDescription("nasilsiniz gençler");
        tweet2.setCreatedOn(Timestamp.valueOf("2016-11-16 06:43:19.77"));
        tweetService.addTweet(tweet2);
        tweetService.addTweet(tweet);
        return ResponseEntity.ok(userRepository.findAll());
    }
    */

}
