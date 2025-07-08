package com.dev.sistemaCadastro.controller;

import com.dev.sistemaCadastro.domain.Gender;
import com.dev.sistemaCadastro.domain.TypePet;
import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.model.PetModel;
import com.dev.sistemaCadastro.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PetControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private PetDto petDto;
    @Mock
    private PetModel petModel;


    @InjectMocks
    PetController petController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        petDto = PetDto.builder()
                .firstName("MARIA")
                .lastName("EDUARDA")
                .age(10)
                .typePet(TypePet.CAT)
                .breed("vira-lata")
                .weight(new BigDecimal(20))
                .gender(Gender.FEMALE)
                .street("Teste").city("Cidade")
                .numberHouse("200")
                .build();

        petModel.setFirstName("MARIA");
        petModel.setLastName("EDUARDA");
        petModel.setAge(10);
        petModel.setWeight(new BigDecimal(20));
        petModel.setBreed("vira-lata");
        petModel.setTypePet(TypePet.CAT);
        petModel.setGender(Gender.FEMALE);
        petModel.setStreet("Teste");
        petModel.setCity("Cidade");
        petModel.setNumberHouse("200");

        when(petService.savePet(petDto)).thenReturn(petModel);
    }


    @Test
    void savePet_ShouldNotReturnNullResponse() {
        ResponseEntity<PetModel> response = petController.savePet(petDto);
        assertNotNull(response);
    }

    @Test
    void savePet_ShouldReturnResponseCreated() {
        ResponseEntity<PetModel> response = petController.savePet(petDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void savePet_ShouldReturnNonNullBody() {
        ResponseEntity<PetModel> response = petController.savePet(petDto);
        assertNotNull(response.getBody());
    }

    @Test
    void getAllPets_ShouldReturnStatusOk() {
        ResponseEntity<List<PetModel>> response = petController.getAllPets();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deletePetByID_ShouldReturnStatusOk() {
        Long idToDelete = 1L;
        petModel.setId(idToDelete);

        when(petService.getOnePetByID(idToDelete)).thenReturn(Optional.of(petModel));

        ResponseEntity<Object> response = petController.deletePetByID(idToDelete);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deletePetByID_ShouldReturnStatusNotFound() {

        Long idInexistente = 99L;

        ResponseEntity<Object> response = petController.deletePetByID(petModel.getId());
        when(petService.getOnePetByID(idInexistente)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updatePetByID_ShouldReturnStatusNotFound() {

        Long idInexistente = 99L;
        
        ResponseEntity<Object> response = petController.updatePetByID(petModel.getId(), petDto);
        when(petService.getOnePetByID(idInexistente)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updatePetByID_ShouldReturnStatusOk() {
        Long idToUpdate = 1L;
        petModel.setId(idToUpdate);

        when(petService.getOnePetByID(idToUpdate)).thenReturn(Optional.of(petModel));

        ResponseEntity<Object> response = petController.updatePetByID(idToUpdate, petDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}