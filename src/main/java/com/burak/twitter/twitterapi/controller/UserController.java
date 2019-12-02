package com.burak.twitter.twitterapi.controller;

import com.burak.twitter.twitterapi.dto.TweetDto;
import com.burak.twitter.twitterapi.dto.UserDetailDto;
import com.burak.twitter.twitterapi.dto.UserDto;
import com.burak.twitter.twitterapi.dto.UserDtoRequest;
import com.burak.twitter.twitterapi.entity.Follow;
import com.burak.twitter.twitterapi.entity.User;
import com.burak.twitter.twitterapi.entity.UserFav;
import com.burak.twitter.twitterapi.helper.BusinessLayerResult;
import com.burak.twitter.twitterapi.helper.Favorite;
import com.burak.twitter.twitterapi.repository.FavoriteRepository;
import com.burak.twitter.twitterapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;
    private final FavoriteService favoriteService;
    private final FollowService followService;
    private final ReTweetService retweetService;
    private final TweetService tweetService;
    public UserController(UserService userService,FavoriteService favoriteService,FollowService followService,ReTweetService retweetService,TweetService tweetService){
        this.userService=userService;
        this.favoriteService=favoriteService;
        this.followService=followService;
        this.retweetService=retweetService;
        this.tweetService=tweetService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/allUsers") //doğru
    public ResponseEntity<List<UserDto>> allUsers(){
        return  ResponseEntity.ok(userService.findAll());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}") //doğru
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public BusinessLayerResult<UserDto> getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public BusinessLayerResult<UserDto> updateUser(@RequestBody UserDtoRequest userDtoRequest, @PathVariable("id") Long id){
        return userService.updateUser(userDtoRequest,id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/",produces = "application/json")
    public BusinessLayerResult<UserDto> addUser(@RequestBody  UserDtoRequest userDtoRequest){
        return userService.addUser(userDtoRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/fav/{userId}")
    public List<String> getFavorites(@PathVariable("userId") Long userId){
        return favoriteService.getAllByUserId(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/followers/{userId}")
    public List<UserDto> getFollowersById(@PathVariable("userId") Long userId){
        return  followService.getFollowersByUserId(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/follows/{userId}")
    public List<UserDto> getFollowsById(@PathVariable("userId") Long userId){
        return  followService.getFollowByUserId(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/follow/{followerId}/{followId}")
    public ResponseEntity<Boolean> checkFollow(@PathVariable("followerId")Long followerId,@PathVariable("followId") Long followId){
        return ResponseEntity.ok(followService.checkFollow(followerId,followId));
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value ="/{userId}/{targetId}")
    public ResponseEntity<String> addFollow(@PathVariable("userId")Long userId,@PathVariable("targetId")Long targetId){
        return ResponseEntity.ok(followService.addFollowOrUnfollow(userId,targetId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/retweet/{userId}/{tweetId}")
    public ResponseEntity<String> reTweet(@PathVariable("userId") Long userId,@PathVariable("tweetId") Long tweetId){
        return ResponseEntity.ok(retweetService.addReTweetOrDelete(userId,tweetId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/advicefollow/{userId}")
    public ResponseEntity<List<UserDto>> reTweet(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.adviceUserss(userId));
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/myFollowsTweets/{userId}")
    public BusinessLayerResult<List<TweetDto>> myFollowsTweet(@PathVariable("userId") Long userId){
        return tweetService.getAllFollowsTweetByUserId(userId);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/detail/{userId}")
    public UserDetailDto detail(@PathVariable("userId") Long userId){
       return userService.userDetails(userId);
    }



    /*@GetMapping(value = "/fav")
    public List<UserFav> favs(){
        return favoriteRepository.findAll();
    }*/

}
