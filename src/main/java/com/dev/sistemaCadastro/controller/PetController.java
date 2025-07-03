package com.dev.sistemaCadastro.controller;


import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.model.PetModel;
import com.dev.sistemaCadastro.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping("/pet")
    public ResponseEntity<PetModel> savePet(@RequestBody @Valid PetDto petDto){
        return petService.savePet(petDto);
    }

    @GetMapping("/pet")
    public ResponseEntity<List<PetModel>> getAllPets(){
        return petService.getAllPets();
    }

    @DeleteMapping("/pet/{id}")
    public ResponseEntity<Object> deletePetByID(@PathVariable(value = "id") UUID id){
        return petService.deletePet(id);
    }

    @PutMapping("/pet/{id}")
    public ResponseEntity<Object> updatePetByID(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid PetDto petDto){
        return petService.updatePet(id,petDto);
    }
}
