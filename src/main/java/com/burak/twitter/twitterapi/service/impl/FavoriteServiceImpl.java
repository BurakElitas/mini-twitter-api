package com.burak.twitter.twitterapi.service.impl;

import com.burak.twitter.twitterapi.dto.UserFavDto;
import com.burak.twitter.twitterapi.entity.User;
import com.burak.twitter.twitterapi.entity.UserFav;
import com.burak.twitter.twitterapi.helper.BusinessLayerResult;
import com.burak.twitter.twitterapi.helper.Favorite;
import com.burak.twitter.twitterapi.repository.FavoriteRepository;
import com.burak.twitter.twitterapi.repository.UserRepository;
import com.burak.twitter.twitterapi.service.FavoriteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @PersistenceContext
    private EntityManager entityManager;
    private final FavoriteRepository favoriteRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,ModelMapper modelMapper,UserRepository userRepository){
        this.favoriteRepository=favoriteRepository;
        this.modelMapper=modelMapper;
        this.userRepository=userRepository;
    }

    @Override
    public List<String> getAllByUserId(Long userId) { //TODO
        BusinessLayerResult<List<UserFavDto>> result=new BusinessLayerResult<>();
        User user=userRepository.findUserById(userId);
        List<Object> dto=favoriteRepository.findAllByUserId(user);
        List<String> userFavs=new ArrayList<>();
        for (Object o:dto ) {
            Object[] obj= (Object[]) o;

            for (Object o2:obj) {
                userFavs.add(o2.toString());
            }
            userFavs.add("");

        }
        return null;
    }
}
