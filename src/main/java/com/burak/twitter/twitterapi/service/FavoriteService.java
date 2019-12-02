package com.burak.twitter.twitterapi.service;

import com.burak.twitter.twitterapi.dto.UserFavDto;
import com.burak.twitter.twitterapi.entity.UserFav;
import com.burak.twitter.twitterapi.helper.BusinessLayerResult;
import com.burak.twitter.twitterapi.helper.Favorite;

import java.util.List;

public interface FavoriteService {
    List<String> getAllByUserId(Long userId);
}
