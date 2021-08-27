package com.loizenai.jwtauthentication.repository;

import com.loizenai.jwtauthentication.model.Collection;

import com.loizenai.jwtauthentication.model.Item;
import com.loizenai.jwtauthentication.model.Like;
import com.loizenai.jwtauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    @Query("SELECT u FROM Collection u WHERE u.user = ?1")
    public List<Collection> findCollectionByIdUser(User user);

    @Query("SELECT u FROM Collection u WHERE u.user = ?1")
    public List<Collection> findBigestCollection(User user);
}
