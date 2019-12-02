package com.burak.twitter.twitterapi.service.impl;

import com.burak.twitter.twitterapi.entity.ReTweet;
import com.burak.twitter.twitterapi.entity.Tweet;
import com.burak.twitter.twitterapi.repository.ReTweetRepository;
import com.burak.twitter.twitterapi.repository.TweetRepository;
import com.burak.twitter.twitterapi.service.ReTweetService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ReTweetServiceImpl implements ReTweetService {

    private final ReTweetRepository reTweetRepository;
    private final TweetRepository tweetRepository;

    public ReTweetServiceImpl(ReTweetRepository reTweetRepository, TweetRepository tweetRepository){
        this.reTweetRepository=reTweetRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public String addReTweetOrDelete(Long userId, Long tweetId) {
        ReTweet reTweet=reTweetRepository.findByUserIdAndTweetId(userId,tweetId);
        Tweet tweet=tweetRepository.findTweetById(tweetId);
        if(reTweet!=null){
            reTweetRepository.delete(reTweet);
            tweet.setReetweetCount(tweet.getReetweetCount()-1);
            tweetRepository.save(tweet);
            return "{\"result\":\"true\",\"state\":\"deleted\"}";
        }
        else{
            reTweet=new ReTweet();
            reTweet.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
            reTweet.setTweetId(tweetId);
            reTweet.setUserId(userId);
            reTweetRepository.save(reTweet);
            tweet.setReetweetCount(tweet.getReetweetCount()+1);
            tweetRepository.save(tweet);
            if(reTweet.getId()!=null)
                return "{\"result\":\"true\",\"state\":\"added\"}";

        }
        return "{\"result\":\"false\",\"state\":\"added error\"}";
    }

}
