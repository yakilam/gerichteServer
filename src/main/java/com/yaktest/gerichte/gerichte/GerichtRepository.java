package com.yaktest.gerichte.gerichte;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GerichtRepository extends MongoRepository<Gericht, String> {

    Optional<Gericht> findGerichtByName(String gerichtName);

    Optional<Gericht> findGerichtById(String id);

    List<Gericht> findGerichtByAuthor(String author);

    Optional<Integer> countByAuthor(String author);

    @Query("{ $or: [ { 'author': ?0 } , { 'isOpen': 'true' } ]}")
    List<Gericht> findGerichtByAuthorOrIsOpen(String author);



}
