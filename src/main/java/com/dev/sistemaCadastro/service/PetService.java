package com.dev.sistemaCadastro.service;


import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.exceptions.ResourceNotFoundException;
import com.dev.sistemaCadastro.model.PetModel;
import com.dev.sistemaCadastro.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;
    PetDto petDto;

    public PetModel savePet(@RequestBody @Valid PetDto petDto) {
        var petModel = new PetModel();
        BeanUtils.copyProperties(petDto, petModel);
        return petRepository.save(petModel);
    }

    public List<PetModel> getAllPets() {
        return petRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void getAllPetsDTO() {
        List<PetModel> all = petRepository.findAll();
        for (PetModel petModel : all) {
            PetDto build = PetDto.builder().firstName(petModel.getFirstName())
                    .lastName(petModel.getLastName())
                    .age(petModel.getAge())
                    .weight(petModel.getWeight())
                    .breed(petModel.getBreed())
                    .gender(petModel.getGender())
                    .typePet(petModel.getTypePet())
                    .numberHouse(petModel.getNumberHouse())
                    .street(petModel.getStreet())
                    .city(petModel.getCity())
                    .build();
            System.out.println("ID: " + petModel.getId());
            System.out.println(build);
        }

    }
    public PetDto buildPetModelIntoPetDto(PetModel petModel) {
        return PetDto.builder().firstName(petModel.getFirstName())
                .lastName(petModel.getLastName())
                .age(petModel.getAge())
                .weight(petModel.getWeight())
                .breed(petModel.getBreed())
                .gender(petModel.getGender())
                .typePet(petModel.getTypePet())
                .build();
    }

    @Transactional(readOnly = true)
    public PetDto findByID(Long id) {
        PetModel petModel = petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet nao encontrado"));
        return  PetDto.builder().firstName(petModel.getFirstName())
                .lastName(petModel.getLastName())
                .age(petModel.getAge())
                .weight(petModel.getWeight())
                .breed(petModel.getBreed())
                .gender(petModel.getGender())
                .typePet(petModel.getTypePet())
                .numberHouse(petModel.getNumberHouse())
                .street(petModel.getStreet())
                .city(petModel.getCity())
                .build();
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    public Optional<PetModel> getOnePetByID(Long id){
        return petRepository.findById(id);
    }

    public void updatePet(Long id, PetDto petDto) {
        var petModel = new PetModel();
        BeanUtils.copyProperties(petDto, petModel);
        deletePet(id);
        petRepository.save(petModel);
    }
}
