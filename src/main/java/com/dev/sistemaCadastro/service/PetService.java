package com.dev.sistemaCadastro.service;


import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.model.PetModel;
import com.dev.sistemaCadastro.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public ResponseEntity<PetModel> savePet(@RequestBody @Valid PetDto petDto){
        var petModel = new PetModel();
        BeanUtils.copyProperties(petDto, petModel);
        return  ResponseEntity.status(HttpStatus.CREATED).body(petRepository.save(petModel));
    }

    public ResponseEntity<List<PetModel>> getAllPets(){
        List<PetModel> allPets = petRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allPets);
    }

    public ResponseEntity<Object> filterPetListByFirstName(@PathVariable(value = "first_name") String petName){
        List<PetModel> filterList = new ArrayList<>();
        List<PetModel> petList = petRepository.findAll();
        if (!petList.isEmpty()){
        for (PetModel pet : petList){
            if (pet.getFirstName().contains(petName)){
                filterList.add(pet);
            }
        }
        if (!filterList.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(filterList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet Not Found");
    }
}
