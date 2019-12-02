package com.burak.twitter.twitterapi.controller;

import com.burak.twitter.twitterapi.dto.TweetDto;
import com.burak.twitter.twitterapi.helper.BusinessLayerResult;
import com.burak.twitter.twitterapi.service.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tweet")
@CrossOrigin(origins ="http://localhost:3000" )
public class TweetController {
    private final TweetService tweetService;

    public TweetController(TweetService tweetService){
        this.tweetService=tweetService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}")
    public BusinessLayerResult<TweetDto> addTweet(@RequestBody TweetDto tweetDto, @PathVariable("userId") Long userId){
        System.out.println("baran");
        return tweetService.addTweet(tweetDto,userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/delete/{tweetId}")
    public ResponseEntity<?> deleteTweet(@PathVariable("tweetId") Long tweetId){

        tweetService.deleteTweet(tweetId);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tweets/{userId}")
    public BusinessLayerResult<List<TweetDto>> getTweetsByUser(@PathVariable("userId") Long userId){
        return tweetService.getAllTweetByUserId(userId);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessLayerResult<TweetDto> getTweetById(@PathVariable("id") Long id){
        return tweetService.getTweetById(id);
    }


}
