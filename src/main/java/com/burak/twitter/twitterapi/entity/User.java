package com.burak.twitter.twitterapi.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="User")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name",nullable = false,length = 30)
    private String name;

    @Column(name = "Surname",nullable = false,length = 30)
    private String surname;

    @Column(name = "Username",nullable = false,length = 15,unique = true)
    private String username;

    @Column(name = "password",nullable = false,length = 70)
    private String password;

    @Column(name = "Email",nullable = false,unique = true)
    @Email
    private String email;



    @Column(name = "CreatedOn",nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "ModifiedOn",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedOn;

    @Column(name = "IsActive",nullable = false)
    private Boolean isActive=true;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Tweet> tweets=new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<UserFav> userFavs=new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "followId")
    private List<Follow> follows=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "followerId")
    private List<Follow> followers=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<ReTweet> reTweets=new ArrayList<>();

    public User(){
        LocalDateTime localDateTime= LocalDateTime.now();
        this.createdOn= Timestamp.valueOf(localDateTime);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<UserFav> getUserFavs() {
        return userFavs;
    }

    public void setUserFavs(List<UserFav> userFavs) {
        this.userFavs = userFavs;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }

    public List<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follow> followers) {
        this.followers = followers;
    }

    public List<ReTweet> getReTweets() {
        return reTweets;
    }

    public void setReTweets(List<ReTweet> reTweets) {
        this.reTweets = reTweets;
    }
}
