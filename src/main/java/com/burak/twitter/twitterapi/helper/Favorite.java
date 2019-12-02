package com.burak.twitter.twitterapi.helper;


import lombok.Data;

import java.util.Date;

@Data
public class Favorite {
    private Long id;
    private Date createdOn;
    private Date tCreated;
    private String description;
    private int commentCount;
    private int favCount;
    private int likeCount;
    private String name;
    private String surname;
    private String username;

}
