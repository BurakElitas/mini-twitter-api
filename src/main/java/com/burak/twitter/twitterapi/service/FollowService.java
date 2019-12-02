package com.burak.twitter.twitterapi.service;

import com.burak.twitter.twitterapi.dto.UserDto;

import java.util.List;

public interface FollowService {
    List<UserDto> getFollowersByUserId(Long userId);
    List<UserDto> getFollowByUserId(Long userId);
    String addFollowOrUnfollow(Long userId,Long targetId);
    Boolean checkFollow(Long followerId,Long followId);
}
