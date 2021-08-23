package com.loizenai.jwtauthentication.repository;

import com.loizenai.jwtauthentication.model.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
