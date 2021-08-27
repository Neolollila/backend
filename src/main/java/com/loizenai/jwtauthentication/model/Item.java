package com.loizenai.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String name;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_collection")
    @JsonBackReference
    private Collection collection;

    @Getter
    @Setter
    @OneToMany (mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Comment> comments=new ArrayList<>();

    @Getter
    @Setter
    @OneToMany (mappedBy = "item", fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Like> likes =new ArrayList<>();


    public Item() {};
}
