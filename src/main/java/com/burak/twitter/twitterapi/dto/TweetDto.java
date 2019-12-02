package com.burak.twitter.twitterapi.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TweetDto {
    private Long id;
    private String description;
    private int commentCount=0;
    private int reetweetCount=0;
    private int favCount=0;
    private Date createdOn;
    private UserDto userDto;
    private String RtUsername;
    private List<TweetDto> tweetDtos=new ArrayList<>();
    private Boolean IsRetweet;
    private Long RtUserId;
    private Long tweetUniqueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<TweetDto> getTweetDtos() {
        return tweetDtos;
    }

    public void setTweetDtos(List<TweetDto> tweetDtos) {
        this.tweetDtos = tweetDtos;
    }

    public int getReetweetCount() {
        return reetweetCount;
    }

    public void setReetweetCount(int reetweetCount) {
        this.reetweetCount = reetweetCount;
    }

    public Boolean getRetweet() {
        return IsRetweet;
    }

    public void setRetweet(Boolean retweet) {
        IsRetweet = retweet;
    }

    public String getRtUsername() {
        return RtUsername;
    }

    public void setRtUsername(String rtUsername) {
        RtUsername = rtUsername;
    }

    public Long getRtUserId() {
        return RtUserId;
    }

    public void setRtUserId(Long rtUserId) {
        RtUserId = rtUserId;
    }

    public Long getTweetUniqueId() {
        return tweetUniqueId;
    }

    public void setTweetUniqueId(Long tweetUniqueId) {
        this.tweetUniqueId = tweetUniqueId;
    }
}
