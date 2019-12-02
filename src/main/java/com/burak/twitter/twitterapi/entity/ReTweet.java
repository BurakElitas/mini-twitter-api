package com.burak.twitter.twitterapi.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Retweet")
public class ReTweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="userId")
    private Long userId;

    @Column(name = "tweetId")
    private Long tweetId;

    @Column(name = "createdOn",nullable = false)
    private Date createdOn;


    public ReTweet(){
        LocalDateTime localDateTime= LocalDateTime.now();
        this.createdOn= Timestamp.valueOf(localDateTime);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
