package com.burak.twitter.twitterapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Favori")
@Data
public class UserFav{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "User_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "Tweet_id",nullable = false)
    private Tweet tweet;

    @Column(name = "CreatedOn",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

}
