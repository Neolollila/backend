package com.loizenai.jwtauthentication.repository;

import com.loizenai.jwtauthentication.model.Fielditem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FielditemRepository extends JpaRepository<Fielditem, Long> {
}
