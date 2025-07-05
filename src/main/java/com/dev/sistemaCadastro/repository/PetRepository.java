package com.dev.sistemaCadastro.repository;

import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.model.PetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PetRepository extends JpaRepository<PetModel, Long> {
}
