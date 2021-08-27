package com.loizenai.jwtauthentication.repository;

import com.loizenai.jwtauthentication.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository  extends JpaRepository<Item, Long> {
    @Query(value="select u from Item u")
    public List<Item> lastAddedItems(Pageable pageable);
}
