package com.loizenai.jwtauthentication.repository;

import com.loizenai.jwtauthentication.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
