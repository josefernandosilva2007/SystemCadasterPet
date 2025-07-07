package com.dev.sistemaCadastro.dto;


import com.dev.sistemaCadastro.domain.Gender;
import com.dev.sistemaCadastro.domain.TypePet;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Builder

@Data
public class PetDto {
    public String firstName;
    public String lastName;
    public Integer age;
    public BigDecimal weight;
    public String  breed;
    public Gender gender;
    public TypePet typePet;
    public String city;
    public String street;
    public String numberHouse;
}

