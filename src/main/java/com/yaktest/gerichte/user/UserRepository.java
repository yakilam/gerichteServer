package com.yaktest.gerichte.user;

import com.yaktest.gerichte.gerichte.Gericht;
import com.yaktest.gerichte.gerichte.GerichtZutat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByUserName(String userName);

    @Query("{ 'userName': ?0 }")
    @Update("{'$set': {'zutaten': ?1}}")
    Integer updateUserZutaten(String userName, List<GerichtZutat> gerichtZutat);

    @Query("{ 'userName': ?0 }")
    @Update("{'$set': {'userPlan': ?1}}")
    Integer updateUserPlan(String userName, List<Gericht> userPlan);

}
