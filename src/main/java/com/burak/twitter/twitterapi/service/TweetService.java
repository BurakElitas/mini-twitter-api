package com.burak.twitter.twitterapi.service;

import com.burak.twitter.twitterapi.dto.TweetDto;
import com.burak.twitter.twitterapi.entity.Tweet;
import com.burak.twitter.twitterapi.helper.BusinessLayerResult;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetService {
    BusinessLayerResult<TweetDto> addTweet(TweetDto tweetDto, Long userId);
    Boolean deleteTweet(Long id);
    BusinessLayerResult<List<TweetDto>> findAllByUserId(Long userid);
    BusinessLayerResult<TweetDto> getTweetById(Long tweetId);


    BusinessLayerResult<List<TweetDto>> getAllFollowsTweetByUserId(Long userId);
    BusinessLayerResult<List<TweetDto>> getAllTweetByUserId(Long userId);
}
