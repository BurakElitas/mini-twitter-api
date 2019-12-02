package com.burak.twitter.twitterapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Follow")
@Data
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "followerId")
    private Long followerId;

    @Column(name = "followId")
    private Long followId;

}
