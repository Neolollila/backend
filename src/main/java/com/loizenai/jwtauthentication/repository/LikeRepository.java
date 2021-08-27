package com.loizenai.jwtauthentication.repository;

import com.loizenai.jwtauthentication.model.Item;
import com.loizenai.jwtauthentication.model.Like;
import com.loizenai.jwtauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;



@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT u FROM Like u WHERE u.user = ?1 and u.item = ?2")
    public Like findByIdUserAndItem(User user, Item item);

    @Query("SELECT COUNT(u) FROM Like u WHERE u.item=?1")
    public String countLikes(Item item);

    @Modifying
    @Transactional
    @Query("delete from Like u where u.id in ?1")
    void deleteLikeWithId(Long id);
}
