package com.loizenai.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "fielditem")
public class Fielditem {

    @Getter
    @Setter
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotBlank
    private String value;

    @Getter
    @Setter
    @Column(name = "isvisible", columnDefinition = "boolean default true")
    private Boolean isvisible = true;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_item")
    @JsonBackReference
    private Item item;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_type")
    @JsonBackReference
    private Type type;

    public Fielditem() {};
}
