package com.loizenai.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class Item {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Getter
    @Setter
    @NotBlank
    @Size(min = 1)
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "item_tag",
//        joinColumns = @JoinColumn(name = "item_id"),
//        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private String name;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_collection")
    @JsonBackReference
    private Collection collection;



    public Item() {};
}
