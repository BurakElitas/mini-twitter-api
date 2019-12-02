package com.burak.twitter.twitterapi.dto;


import com.burak.twitter.twitterapi.dto.TweetDto;
import com.burak.twitter.twitterapi.dto.UserDto;

import java.util.ArrayList;
import java.util.Date;

public class UserFavDto {
    private Long id;
    private UserDto userDto;
    private TweetDto tweetDto;
    private Date createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public TweetDto getTweetDto() {
        return tweetDto;
    }

    public void setTweetDto(TweetDto tweetDto) {
        this.tweetDto = tweetDto;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
