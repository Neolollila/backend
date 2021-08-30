package com.loizenai.jwtauthentication.repository;


import com.loizenai.jwtauthentication.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
}
