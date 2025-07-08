package com.dev.sistemaCadastro.controller;


import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.model.PetModel;
import com.dev.sistemaCadastro.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping("/pet")
    public ResponseEntity<PetModel> savePet(@RequestBody @Valid PetDto petDto){
        return ResponseEntity.status(HttpStatus.OK).body(petService.savePet(petDto));
    }

    @GetMapping("/pet")
    public ResponseEntity<List<PetModel>> getAllPets(){
        return ResponseEntity.status(HttpStatus.OK).body(petService.getAllPets());
    }

    @DeleteMapping("/pet/{id}")
    public ResponseEntity<Object> deletePetByID(@PathVariable(value = "id") Long id){
        Optional<PetModel> onePetByID = petService.getOnePetByID(id);
        if (onePetByID.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not Found");
        }
        petService.deletePet(id);
        return ResponseEntity.status(HttpStatus.OK).body("Pet Deleted Successfully");
    }

    @PutMapping("/pet/{id}")
    public ResponseEntity<Object> updatePetByID(@PathVariable(value = "id") Long id,
                                                @RequestBody @Valid PetDto petDto){
        Optional<PetModel> onePetByID = petService.getOnePetByID(id);
        if (onePetByID.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not Found");
        }
        petService.updatePet(id, petDto);
        return ResponseEntity.status(HttpStatus.OK).body("Pet Updated Successfully");
    }
}
