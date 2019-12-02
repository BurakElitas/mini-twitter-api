package com.burak.twitter.twitterapi.service.impl;

import com.burak.twitter.twitterapi.controller.TweetController;
import com.burak.twitter.twitterapi.dto.TweetDto;
import com.burak.twitter.twitterapi.dto.UserDetailDto;
import com.burak.twitter.twitterapi.dto.UserDto;
import com.burak.twitter.twitterapi.dto.UserDtoRequest;
import com.burak.twitter.twitterapi.entity.Follow;
import com.burak.twitter.twitterapi.entity.User;
import com.burak.twitter.twitterapi.helper.BusinessLayerResult;
import com.burak.twitter.twitterapi.repository.FollowRepository;
import com.burak.twitter.twitterapi.repository.TweetRepository;
import com.burak.twitter.twitterapi.repository.UserRepository;
import com.burak.twitter.twitterapi.service.TweetService;
import com.burak.twitter.twitterapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TweetService tweetService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FollowRepository followRepository;
    private final TweetRepository tweetRepository;

    public UserServiceImpl(UserRepository userRepository, TweetRepository tweetRepository, ModelMapper modelMapper, TweetService tweetService, BCryptPasswordEncoder bCryptPasswordEncoder, FollowRepository followRepository){
        this.userRepository = userRepository;
        this.modelMapper=modelMapper;
        this.tweetService = tweetService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.followRepository = followRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public BusinessLayerResult<UserDto> addUser(UserDtoRequest userDtoRequest) {
        BusinessLayerResult<UserDto> result=new BusinessLayerResult<>();
        LocalDateTime localDateTime=LocalDateTime.now();
        User user=userRepository.findByUsername(userDtoRequest.getUsername());
        if(user!=null){
            result.getErrorMessages().add("Bu kullanıcı adı kullanılmaktadır.");
            return result;
        }
        user=userRepository.findByEmail(userDtoRequest.getEmail());
        if(user!=null){
            result.getErrorMessages().add("Bu email adresi kullanılmaktadir.");
            return result;
        }

        user=modelMapper.map(userDtoRequest,User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedOn(Timestamp.valueOf(localDateTime));
        user.setModifiedOn(Timestamp.valueOf(localDateTime));
        UserDto userD=modelMapper.map(userRepository.save(user),UserDto.class);
        Follow follow=new Follow();
        follow.setFollowId(userD.getId());
        follow.setFollowerId(userD.getId());
        followRepository.save(follow);

        result.setResult(userD);
        return result;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAllByIsActiveIsTrue().stream()
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteUser(Long id) {
        User user=userRepository.getOne(id);
        user.setActive(false);
        userRepository.save(user);
       if(user.getActive()==false)
           return true;
        return false;
    }

    @Override
    public BusinessLayerResult<UserDto> getUserById(Long id) {
        BusinessLayerResult<UserDto> result=new BusinessLayerResult<>();
        User user=userRepository.getOne(id);
        if(user==null){
            result.getErrorMessages().add("Kullanıcı bulunamadı.");
            return  result;
        }

        UserDto userDto=modelMapper.map(user,UserDto.class);
        BusinessLayerResult<List<TweetDto>> userTweets= tweetService.getAllTweetByUserId(id);
        result.setResult(userDto);
        return result;
    }

    @Override
    public BusinessLayerResult<UserDto> updateUser(UserDtoRequest userDtoRequest,Long id) {
        BusinessLayerResult<UserDto> result=new BusinessLayerResult<>();
        User user=userRepository.getOne(id); //güncellenecek kullanıcı
        if(user==null) {
            result.getErrorMessages().add("Kullanıcı Bulunamadı.");
           return result;
        }
        user=userRepository.findByEmail(userDtoRequest.getEmail());
        if(user!=null && user.getId()!=id){
            result.getErrorMessages().add("Bu email adresi kullanılmaktadır.Lütfen başka bir tane deneyin.");
            return result;
        }
        user=userRepository.findByUsername(userDtoRequest.getUsername());
        if(user!=null && user.getId()!=id){
            result.getErrorMessages().add("Bu kullanıcı adı kullanılmaktadır.Lütfen başka bir tane deneyin.");
            return result;
        }
        LocalDateTime localDateTime=LocalDateTime.now();
        user=userRepository.getOne(id);
        user.setEmail(userDtoRequest.getEmail());
        user.setName(userDtoRequest.getName());
        user.setSurname(userDtoRequest.getSurname());
        user.setPassword(bCryptPasswordEncoder.encode(userDtoRequest.getPassword()));
        user.setModifiedOn(Timestamp.valueOf(localDateTime));
        user.setUsername(userDtoRequest.getUsername());
        userRepository.save(user);

        UserDto userDto=modelMapper.map(user,UserDto.class);
        result.setResult(userDto);
        return result;
    }

    @Override
    public UserDetailDto userDetails(Long id) {
        User user=userRepository.findUserById(id);
        UserDetailDto userDetailDto=modelMapper.map(user,UserDetailDto.class);
        List<Follow> follows=followRepository.findAllByFollowerId(id);
        List<Follow> followers=followRepository.findAllByFollowId(id);

        if(followers.size()>0){
            userDetailDto.setFollowerCount(followers.size()-1);
        }
        else
            userDetailDto.setFollowerCount(followers.size());
        if(follows.size()>0)
            userDetailDto.setFollowCount(follows.size()-1);
       else
            userDetailDto.setFollowCount(follows.size());


        return userDetailDto;
    }

    @Override
    public List<UserDto> adviceUserss(Long id) {
       List<User> users = userRepository.adviceUser(id);

       return users.stream()
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }
}
