package com.loizenai.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "themes")
public class Theme {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @OneToMany ( fetch = FetchType.LAZY) //mappedBy = "theme",
//    @JsonManagedReference
    private List<Collection> collections=new ArrayList<>();
}
