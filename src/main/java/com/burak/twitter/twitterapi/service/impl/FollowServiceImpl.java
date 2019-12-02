package com.burak.twitter.twitterapi.service.impl;

import com.burak.twitter.twitterapi.dto.UserDto;
import com.burak.twitter.twitterapi.entity.Follow;
import com.burak.twitter.twitterapi.entity.User;
import com.burak.twitter.twitterapi.repository.FollowRepository;
import com.burak.twitter.twitterapi.repository.UserRepository;
import com.burak.twitter.twitterapi.service.FollowService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FollowServiceImpl(FollowRepository followRepository, UserRepository userRepository,ModelMapper modelMapper){
        this.followRepository=followRepository;
        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<UserDto> getFollowersByUserId(Long userId) {
        List<Follow> follows=followRepository.findAllByFollowId(userId);
        List<UserDto> followers=new ArrayList<>();
        for (Follow follow:follows) {
           UserDto userDto= modelMapper.map(userRepository.findUserById(follow.getFollowerId()),UserDto.class);
           followers.add(userDto);
        }

        return followers;
    }

    @Override
    public List<UserDto> getFollowByUserId(Long userId) {
        List<Follow> follows=followRepository.findAllByFollowerId(userId);
        List<UserDto> myFollows=new ArrayList<>();
        for (Follow follow:follows) {
            UserDto userDto= modelMapper.map(userRepository.findUserById(follow.getFollowId()),UserDto.class);
            myFollows.add(userDto);
        }

        return myFollows;
    }

    @Override
    public String  addFollowOrUnfollow(Long userId, Long targetId) {
        Follow follow=followRepository.findByFollowerIdAndFollowId(userId,targetId);

        if(follow==null){
            Follow newFollow=new Follow();
            newFollow.setFollowerId(userId);
            newFollow.setFollowId(targetId);
            followRepository.save(newFollow);




            return "{\"result\":true,\"state\":\"added\"}";
        }
        else
            followRepository.deleteById(follow.getId());

            return "{\"result\":true, \"state\":\"deleted\"}";
    }

    @Override
    public Boolean checkFollow(Long followerId, Long followId) {
      Follow follow= followRepository.findByFollowerIdAndFollowId(followerId,followId);
      if(follow!=null)
          return true;
      else
          return false;
    }


}
