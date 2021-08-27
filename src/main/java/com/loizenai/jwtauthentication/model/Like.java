package com.loizenai.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "likes")
public class Like {

    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;



    @Getter
    @Setter
    @JoinColumn(name = "id_user")
    @ManyToOne
    @JsonBackReference
    private User user;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_item")
    @JsonBackReference
    private Item item;





}
