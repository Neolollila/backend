package com.loizenai.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "itemtypes")
public class Type {

    public Type() {};

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
    @OneToMany (mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Fielditem> fielditems=new ArrayList<>();


}
