package com.burak.twitter.twitterapi.dto;

import lombok.Data;

@Data
public class UserDetailDto{
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String createdOn;
    private int followerCount;
    private int followCount;
    private String password;




}
