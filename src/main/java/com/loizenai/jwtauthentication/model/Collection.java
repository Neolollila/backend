package com.loizenai.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collections")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id" )
public class Collection {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotBlank
    private String description;



    @Getter
    @Setter
//    @NotBlank
    private String Image;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_theme")
    //@JsonBackReference
    private Theme theme;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private User user;

    @Getter
    @Setter
    @OneToMany (mappedBy = "collection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Item> items=new ArrayList<>();


    public Collection() {};



}
