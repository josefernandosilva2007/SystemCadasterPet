package com.dev.sistemaCadastro.domain;

import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.exceptions.ResourceNotFoundException;
import com.dev.sistemaCadastro.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class Menu implements CommandLineRunner {

    @Autowired
    PetService petService;

    Scanner input = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            showMenu();
            String op = input.nextLine();
            switch (op) {
                case "1":
                    saveNewPet();
                    break;
                case "2":
                    updatePetByID();
                    break;
                case "3":
                    deletePetByID();
                    break;
                case "4":
                    showAllPets();
                    break;
                case "0":
                    System.out.println("SAINDO DO SISTEMA");
                    input.close();
                    return;
                default:
                    System.err.println("OPCAO INVALIDA APERTE ENTER PARA TENTAR NOVAMENTE");
                    input.nextLine();
                    break;
            }
        }
    }


    public void showMenu() {
        System.out.println("---------------------------- MENU ----------------------------\n" +
                "1. Cadastrar um novo pet\n" +
                "2. Alterar os dados do pet cadastrado\n" +
                "3. Deletar um pet cadastrado\n" +
                "4. Listar todos os pets cadastrados\n" +
                "5. Listar pets por algum critério (idade, nome, raça)\n" +
                "0. Sair\n" +
                "ESCOLHA UMA OPÇÂO: ");
    }

    public void saveNewPet() {
        System.out.println("Nome: ");
        String firstName = input.nextLine();
        System.out.println("Sobrenome: ");
        String lastName = input.nextLine();
        System.out.println("Idade: ");
        int age = Integer.parseInt(input.nextLine());
        System.out.println("Peso: ");
        BigDecimal weight = BigDecimal.valueOf(Double.parseDouble(input.nextLine()));
        System.out.println("Raça: ");
        String breed = input.nextLine();
        System.out.println("Genero: ");
        Gender gender = Gender.valueOf(input.nextLine().toUpperCase());
        System.out.println("Type: ");
        TypePet typePet = TypePet.valueOf(input.nextLine().toUpperCase());

        PetDto petDto = PetDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .weight(weight)
                .breed(breed)
                .gender(gender)
                .typePet(typePet)
                .build();

        petService.savePet(petDto);
    }

    public void showAllPets() {
        petService.getAllPetsDTO();

    }

    public void deletePetByID() throws Exception {

        try {
            System.out.println("DIGITE O ID QUE VC DESEJA DELETAR: ");
            Long id = Long.parseLong(input.nextLine());
            petService.findByID(id);
            System.out.println("Tem certeza? y/n");
            String asw = input.nextLine().toLowerCase();
            if (!asw.equals("y") && !asw.equals("n")) {
                System.out.println("DIGITE UMA OPÇÃO VALIDA");
                deletePetByID();
            }
            if (asw.equals("n")) {
                run();
            }
            petService.deletePet(id);
            System.out.println("Pet deletado com sucesso");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("DIGITE O ID QUE VC DESEJA DELETAR: ");
        Long id = Long.parseLong(input.nextLine());
        petService.findByID(id);
        System.out.println("Tem certeza? y/n");
        String asw = input.nextLine().toLowerCase();
        if (!asw.equals("y") && !asw.equals("n")) {
            System.out.println("DIGITE UMA OPÇÃO VALIDA");
            deletePetByID();
        }
        if (asw.equals("n")) {
            run();
        }
        petService.deletePet(id);
        System.out.println("Pet deletado com sucesso");
    }

    public void updatePetByID() {
        try{
            System.out.println("DIGITE O ID QUE VC DESEJA ATUALIZAR: ");
            Long id = Long.parseLong(input.nextLine());
            PetDto byID = petService.findByID(id);
            System.out.println("Nome: ");
            String firstName = input.nextLine();
            if (firstName.isEmpty()){
                firstName = byID.getFirstName();
            }
            System.out.println("Sobrenome: ");
            String lastName = input.nextLine();
            if (lastName.isEmpty()){
                lastName = byID.getLastName();
            }
            System.out.println("Idade: ");
            String ageString = input.nextLine();
            Integer age;
            if (!ageString.isEmpty()){
                age = Integer.parseInt(ageString);
            }
            age = byID.getAge();
            System.out.println("Peso: ");
            String weightString = input.nextLine();
            BigDecimal weight;
            if (!weightString.isEmpty()){
                weight = BigDecimal.valueOf(Double.parseDouble(weightString));
            }
            weight = byID.getWeight();
            System.out.println("Raça: ");
            String breed = input.nextLine();
            if (breed.isEmpty()){
                breed = byID.getBreed();
            }
            System.out.println("Genero [OBRIGATORIO]: ");
            Gender gender = Gender.valueOf(input.nextLine().toUpperCase());
            System.out.println("Type [OBRIGATORIO]: ");
            TypePet typePet = TypePet.valueOf(input.nextLine().toUpperCase());
            PetDto petDto = PetDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .age(age)
                    .weight(weight)
                    .breed(breed)
                    .gender(gender)
                    .typePet(typePet)
                    .build();

            petService.updatePet(id, petDto);


        }catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


}
