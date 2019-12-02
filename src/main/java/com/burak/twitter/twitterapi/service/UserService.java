package com.burak.twitter.twitterapi.service;

import com.burak.twitter.twitterapi.dto.UserDetailDto;
import com.burak.twitter.twitterapi.dto.UserDto;
import com.burak.twitter.twitterapi.dto.UserDtoRequest;
import com.burak.twitter.twitterapi.entity.User;
import com.burak.twitter.twitterapi.helper.BusinessLayerResult;

import java.util.List;

public interface UserService {
    BusinessLayerResult<UserDto> addUser(UserDtoRequest userDtoRequest);
    List<UserDto> findAll();
    Boolean deleteUser(Long id);
    BusinessLayerResult<UserDto> getUserById(Long id);
    BusinessLayerResult<UserDto> updateUser(UserDtoRequest userDtoRequest,Long id);
    UserDetailDto userDetails(Long id);
    List<UserDto> adviceUserss(Long id);
}
