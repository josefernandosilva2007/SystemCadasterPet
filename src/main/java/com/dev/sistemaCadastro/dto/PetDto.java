package com.dev.sistemaCadastro.dto;


import com.dev.sistemaCadastro.domain.Gender;
import com.dev.sistemaCadastro.domain.TypePet;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Builder
@Data
public class PetDto {
    String firstName;
    String lastName;
    Integer age;
    BigDecimal weight;
    String  breed;
    Gender gender;
    TypePet typePet;

}
