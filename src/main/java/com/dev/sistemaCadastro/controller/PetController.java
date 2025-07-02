package com.dev.sistemaCadastro.controller;


import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.model.PetModel;
import com.dev.sistemaCadastro.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
