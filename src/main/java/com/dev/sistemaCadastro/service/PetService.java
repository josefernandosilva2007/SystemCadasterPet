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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;
    PetDto petDto;

    public ResponseEntity<PetModel> savePet(@RequestBody @Valid PetDto petDto) {
        var petModel = new PetModel();
        BeanUtils.copyProperties(petDto, petModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(petRepository.save(petModel));
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
                    .build();
            System.out.println("ID: " + petModel.getId());
            System.out.println(build);
        }

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
                .build();
    }


    public ResponseEntity<Object> filterPetListByFirstName(@PathVariable(value = "first_name") String petName) {
        List<PetModel> filterList = new ArrayList<>();
        List<PetModel> petList = petRepository.findAll();
        if (!petList.isEmpty()) {
            for (PetModel pet : petList) {
                if (pet.getFirstName().contains(petName)) {
                    filterList.add(pet);
                }
            }
            if (!filterList.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(filterList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet Not Found");
    }

    public ResponseEntity<Object> deletePet(Long id) {
        Optional<PetModel> petOp = petRepository.findById(id);
        if (petOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not Found");
        }
        petRepository.delete(petOp.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pet Delete Sucessfully");
    }

    public ResponseEntity<Object> updatePet(Long id, PetDto petDto) {
        Optional<PetModel> petOp = petRepository.findById(id);
        if (petOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet NOT FOUND");
        }
        var petModel = new PetModel();
        BeanUtils.copyProperties(petDto, petModel);
        deletePet(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(petRepository.save(petModel));
    }
}
