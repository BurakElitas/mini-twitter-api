package com.burak.twitter.twitterapi.entity;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "Tweet")
public class Tweet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Description",nullable = false,length = 240)
    private String description;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id")
    private User user;

    @OneToMany(mappedBy = "parentTweetId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Tweet> tweets=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParentTweetId",nullable = true)
    private Tweet parentTweetId;

    @Column(name = "CommentCount")
    private int commentCount=0;

    @Column(name="reetweetCount")
    private int reetweetCount=0;

    @Column(name="FavCount")
    private int favCount=0;

    @Column(name = "CreatedOn",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @OneToMany(mappedBy = "tweet",fetch = FetchType.LAZY) //
    private List<UserFav> tweetFavs=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tweetId")
    private List<ReTweet> reTweets=new ArrayList<>();

    public Tweet(){
        LocalDateTime localDateTime= LocalDateTime.now();
        this.createdOn= Timestamp.valueOf(localDateTime);
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Tweet getParentTweetId() {
        return parentTweetId;
    }

    public void setParentTweetId(Tweet parentTweetId) {
        this.parentTweetId = parentTweetId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getReetweetCount() {
        return reetweetCount;
    }

    public void setReetweetCount(int reetweetCount) {
        this.reetweetCount = reetweetCount;
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

    public List<UserFav> getTweetFavs() {
        return tweetFavs;
    }

    public void setTweetFavs(List<UserFav> tweetFavs) {
        this.tweetFavs = tweetFavs;
    }

    public List<ReTweet> getReTweets() {
        return reTweets;
    }

    public void setReTweets(List<ReTweet> reTweets) {
        this.reTweets = reTweets;
    }
}
