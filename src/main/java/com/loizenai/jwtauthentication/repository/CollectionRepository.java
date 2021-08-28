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
    @Query("SELECT c FROM Collection c WHERE c.user = ?1")
    public List<Collection> findCollectionByIdUser(User user);

    @Query(value = "select items.id_collection as id, count(*) as countitems\n" +
            "from items\n" +
            "group by items.id_collection\n" +
            "order by countitems desc limit 3",nativeQuery = true)
    public List<Long> findBigestCollection();

    @Query("SELECT c FROM Collection c WHERE c.id in ?1")
    public List<Collection> findByIds(List<Long> id);
}
