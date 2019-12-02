package com.burak.twitter.twitterapi.repository;

import com.burak.twitter.twitterapi.entity.User;
import com.burak.twitter.twitterapi.entity.UserFav;
import com.burak.twitter.twitterapi.helper.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<UserFav,Long> {
    //List<UserFav> findAllByUserId(Long userId);

    @Query(value = "select favori.id as id, favori.createdOn as date,tweet.createdOn ,\n" +
            "tweet.description ,tweet.commentCount,tweet.favCount, tweet.reetweetCount, user.name,user.surname,user.username\n" +
            "from UserFav favori right join Tweet tweet\n" +
            "on favori.tweet=tweet.id\n" +
            "inner join User user on tweet.user=user.id\n" +
            " where favori.id is not null and favori.user=:user order by favori.createdOn desc")
    List<Object> findAllByUserId(@Param("user") User user);

}
