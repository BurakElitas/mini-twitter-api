package com.burak.twitter.twitterapi.service.impl;

import com.burak.twitter.twitterapi.dto.TweetDto;
import com.burak.twitter.twitterapi.dto.UserDto;
import com.burak.twitter.twitterapi.entity.Follow;
import com.burak.twitter.twitterapi.entity.ReTweet;
import com.burak.twitter.twitterapi.entity.Tweet;
import com.burak.twitter.twitterapi.entity.User;
import com.burak.twitter.twitterapi.helper.BusinessLayerResult;
import com.burak.twitter.twitterapi.repository.FollowRepository;
import com.burak.twitter.twitterapi.repository.ReTweetRepository;
import com.burak.twitter.twitterapi.repository.TweetRepository;
import com.burak.twitter.twitterapi.repository.UserRepository;
import com.burak.twitter.twitterapi.service.TweetService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetServiceImpl implements TweetService {

    @PersistenceContext
    private EntityManager entityManager;

    private final TweetRepository tweetRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ReTweetRepository reTweetRepository;
    private final FollowRepository followRepository;

    public TweetServiceImpl(TweetRepository tweetRepository, ModelMapper modelMapper, UserRepository userRepository,ReTweetRepository reTweetRepository,FollowRepository followRepository){
        this.tweetRepository=tweetRepository;
        this.modelMapper=modelMapper;
        this.userRepository=userRepository;
        this.reTweetRepository=reTweetRepository;
        this.followRepository=followRepository;
    }


    @Override
    public BusinessLayerResult<TweetDto> addTweet(TweetDto tweetDto, Long userId) {
        BusinessLayerResult<TweetDto> result=new BusinessLayerResult<>();
        if(tweetDto.getDescription().length()>240){
            result.getErrorMessages().add("Tweet 240 karakterden fazla olamaz");
            return result;
        }
        User user=userRepository.getOne(userId);
        Tweet tweet=modelMapper.map(tweetDto,Tweet.class);
        LocalDateTime localDateTime=LocalDateTime.now();
        tweet.setCreatedOn(Timestamp.valueOf(localDateTime));
        tweet.setUser(user);
        tweet=tweetRepository.save(tweet);
        UserDto userDto=modelMapper.map(tweet.getUser(),UserDto.class);

        result.setResult(modelMapper.map(tweet,TweetDto.class));
        result.getResult().setUserDto(userDto);
        return result;
    }

    @Override
    public Boolean deleteTweet(Long id) {
        Tweet tweet=tweetRepository.findTweetById(id);
        if(tweet==null)
            return false;

        if(tweet.getReTweets().size()>0){

            reTweetRepository.deleteAllByTweetId(id);
        }

        tweetRepository.deleteById(id);
        Tweet tweet1=tweetRepository.findTweetById(id);
        if(tweet1==null)
            return true;
        return false;
    }

    @Override
    public BusinessLayerResult<List<TweetDto>> findAllByUserId(Long userid) {
        BusinessLayerResult<List<TweetDto>> result=new BusinessLayerResult<>();
        List<Tweet> userTweets=tweetRepository.findAllByUserIdOrderByCreatedOnDesc(userid);
        List<TweetDto> tweetDtos=userTweets.stream().map(entity->modelMapper.
                map(entity,TweetDto.class)).
                collect(Collectors.toList());
        result.setResult(tweetDtos);
        return result;
    }

    @Override
    public BusinessLayerResult<TweetDto> getTweetById(Long tweetId) {
        BusinessLayerResult<TweetDto> result=new BusinessLayerResult<>();
        Tweet tweet=tweetRepository.findTweetById(tweetId);//ana tweet
        UserDto userDto=modelMapper.map(userRepository.findUserById(tweet.getUser().getId()),UserDto.class);//ana tweet userı
        List<Tweet> tweets=entityManager.createQuery("from Tweet a where parent_tweet_id = :tweetId order by created_on desc", Tweet.class).setParameter("id", tweetId).getResultList();

        List<TweetDto> tweetDtos=new ArrayList<>();
        for (Tweet t:tweets) {
            TweetDto tweetd=modelMapper.map(t,TweetDto.class);
            tweetd.setUserDto(modelMapper.map(userRepository.findUserById(t.getUser().getId()),UserDto.class));
            tweetDtos.add(tweetd);

        }
        TweetDto dto=modelMapper.map(tweet,TweetDto.class);
        dto.setUserDto(userDto);//ana tweet userı
        dto.setTweetDtos(tweetDtos);
        result.setResult(dto);

        return result;
    }

    @Override
    public BusinessLayerResult<List<TweetDto>> getAllFollowsTweetByUserId(Long userId) {
        BusinessLayerResult<List<TweetDto>> result=new BusinessLayerResult<>();
        List<Follow> follows=followRepository.findAllByFollowerId(userId);
        User user=userRepository.findUserById(userId);
        List<Long> ids=new ArrayList<>();
        for (Follow f: follows) {
            ids.add(f.getFollowId());
        }
        System.out.println(ids);
        List<String[]> allTweet= tweetRepository.getAllFollowsTweetByUserId(userId);
        List<TweetDto> tweetDtos=new ArrayList<>();
        for (String[] strings:allTweet) {
            TweetDto tweetDto=new TweetDto();
            tweetDto.setId(Long.parseLong(strings[0]));
            tweetDto.setDescription(strings[1]);
            tweetDto.setCommentCount(Integer.parseInt(strings[2]));
            tweetDto.setFavCount(Integer.parseInt(strings[4]));
            tweetDto.setReetweetCount(Integer.parseInt(strings[3]));
            tweetDto.setCreatedOn(Timestamp.valueOf(strings[5]));
            UserDto userDto=new UserDto();
            userDto.setId(Long.parseLong(strings[6]));
            userDto.setName(strings[7]);
            userDto.setSurname(strings[8]);
            userDto.setUsername(strings[9]);
            tweetDto.setRtUsername(strings[10]);
            if(strings[11]!=null)
            {
                tweetDto.setRtUserId(Long.parseLong(strings[11]));

            }

            if(ids.toString().contains(userDto.getId().toString())==true)
                tweetDto.setRetweet(false);
            else
                tweetDto.setRetweet(true);

            if(tweetDto.getRtUsername()!=null && tweetDto.getRtUsername().equals(user.getUsername().toString())){
                tweetDto.setRetweet(true);
            }
            //if(tweetDto.getRtUsername().equals(user.getUsername())){
                //tweetDto.setRetweet(true);
           // }
            tweetDto.setUserDto(userDto);
            tweetDtos.add(tweetDto);
            Long unique=(new Double(Math.floor(Math.random()*1000000)).longValue());
            tweetDto.setTweetUniqueId(unique);
        }

        result.setResult(tweetDtos);
        return result;


    }

    @Override
    public BusinessLayerResult<List<TweetDto>> getAllTweetByUserId(Long userId) {
        BusinessLayerResult<List<TweetDto>> result=new BusinessLayerResult<>();
        User user=userRepository.findUserById(userId);
        if(reTweetRepository.existsByUserId(userId)==false){

            UserDto userDto=modelMapper.map(user,UserDto.class);
            result=findAllByUserId(userId);
            result.getResult().stream().forEach(entity->entity.setUserDto(userDto));
            result.getResult().stream().forEach(entiy->entiy.setRetweet(false));

            return result;
        }

        List<String[]> allTweet= tweetRepository.getAllTweetByUserId(userId);
        List<TweetDto> tweetDtos=new ArrayList<>();
        for (String[] strings:allTweet) {
            TweetDto tweetDto=new TweetDto();
            tweetDto.setId(Long.parseLong(strings[0]));
            tweetDto.setDescription(strings[1]);
            tweetDto.setCommentCount(Integer.parseInt(strings[2]));
            tweetDto.setFavCount(Integer.parseInt(strings[3]));
            tweetDto.setReetweetCount(Integer.parseInt(strings[4]));
            tweetDto.setCreatedOn(Timestamp.valueOf(strings[5]));
            UserDto userDto=new UserDto();
            userDto.setName(strings[6]);
            userDto.setSurname(strings[7]);
            userDto.setUsername(strings[8]);
            if(strings[9]!=null) {
                userDto.setId(Long.parseLong(strings[9]));
            }

            //userDto.setId(userId);
            if(user.getUsername().equals(userDto.getUsername())==true) {
                tweetDto.setRetweet(false);
            }
            else {
                tweetDto.setRetweet(true);
                tweetDto.setRtUsername(user.getUsername());
                tweetDto.setRtUserId(user.getId());
            }
            tweetDto.setUserDto(userDto);
            tweetDtos.add(tweetDto);

        }
        result.setResult(tweetDtos);
        return result;
    }

}
