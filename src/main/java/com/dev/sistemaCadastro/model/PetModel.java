package com.dev.sistemaCadastro.model;


import com.dev.sistemaCadastro.domain.Gender;
import com.dev.sistemaCadastro.domain.TypePet;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "PETS")
@Getter
@Setter
public class PetModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String firstName;
    String lastName;
    Integer age;
    BigDecimal weight;
    String breed;
    @NotNull
    Gender gender;
    @NotNull
    TypePet typePet;
    @NotNull
    String city;
    @NotNull
    String street;
    String numberHouse;

}
