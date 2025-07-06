package com.dev.sistemaCadastro.domain;

import com.dev.sistemaCadastro.dto.PetDto;
import com.dev.sistemaCadastro.exceptions.ResourceNotFoundException;
import com.dev.sistemaCadastro.model.PetModel;
import com.dev.sistemaCadastro.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Menu implements CommandLineRunner {

    @Autowired
    PetService petService;
    List<PetModel> allPets = new ArrayList<>();
    List<PetDto> petDtoList = new ArrayList<>();

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
                case "5":
                    filterSearch();
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


    private void showMenu() {
        System.out.println("---------------------------- MENU ----------------------------\n" +
                "1. Cadastrar um novo pet\n" +
                "2. Alterar os dados do pet cadastrado\n" +
                "3. Deletar um pet cadastrado\n" +
                "4. Listar todos os pets cadastrados\n" +
                "5. Listar pets por algum critério (idade, nome, raça)\n" +
                "0. Sair\n" +
                "ESCOLHA UMA OPÇÂO: ");
    }

    private void saveNewPet() {
        System.out.println("Nome: ");
        String firstName = input.nextLine().toUpperCase();
        System.out.println("Sobrenome: ");
        String lastName = input.nextLine().toUpperCase();
        System.out.println("Idade: ");
        int age = Integer.parseInt(input.nextLine());
        System.out.println("Peso: ");
        BigDecimal weight = BigDecimal.valueOf(Double.parseDouble(input.nextLine()));
        System.out.println("Raça: ");
        String breed = input.nextLine().toUpperCase();
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

    private void showAllPets() {
        petService.getAllPetsDTO();

    }

    private void deletePetByID() throws Exception {

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

    private void updatePetByID() {
        try {
            System.out.println("DIGITE O ID QUE VC DESEJA ATUALIZAR: ");
            Long id = Long.parseLong(input.nextLine());
            PetDto byID = petService.findByID(id);
            System.out.println("Nome: ");
            String firstName = input.nextLine();
            if (firstName.isEmpty()) {
                firstName = byID.getFirstName();
            }
            System.out.println("Sobrenome: ");
            String lastName = input.nextLine();
            if (lastName.isEmpty()) {
                lastName = byID.getLastName();
            }
            System.out.println("Idade: ");
            String ageString = input.nextLine();
            Integer age;
            if (!ageString.isEmpty()) {
                age = Integer.parseInt(ageString);
            }
            age = byID.getAge();
            System.out.println("Peso: ");
            String weightString = input.nextLine();
            BigDecimal weight;
            if (!weightString.isEmpty()) {
                weight = BigDecimal.valueOf(Double.parseDouble(weightString));
            }
            weight = byID.getWeight();
            System.out.println("Raça: ");
            String breed = input.nextLine();
            if (breed.isEmpty()) {
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


        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private void filterSearch() {
        allPets = petService.getAllPets();
        for (PetModel allPet : allPets.stream().distinct().toList()) {
            petDtoList.add(petService.buildPetModelIntoPetDto(allPet));
        }
        List<PetDto> filterList = new ArrayList<>(petDtoList);

        if (filterList.isEmpty()) {
            System.out.println("Não há pets cadastrados para buscar.");
            return;
        }




        while (true) {
            System.out.println("\n===================================");
            System.out.println(filterList.size() + " pet(s) encontrados com os critérios atuais.");
            formatListPets(filterList); // Mostra a lista atual
            System.out.println("===================================");
            searchMenu();
            String op = input.nextLine();

            if (op.equals("0")) {
                break;
            }

            switch (op) {
                case "1":
                    System.out.println("DIGITE O NOME OU SOBRENOME:");
                    filterList = filterSearchByName(input.nextLine(), filterList);
                    break;
                case "2":
                    System.out.println("DIGITE O SEXO [MALE/FEMALE]:");
                    filterList = filterSearchByGender(input.nextLine(), filterList);
                    break;
                case "3":
                    System.out.println("DIGITE A IDADE:");
                    filterList = filterSearchByAge(input.nextLine(), filterList);
                    break;
                case "4":
                    System.out.println("DIGITE O PESO:");
                    filterList = filterSearchByWeight(input.nextLine(), filterList);
                    break;
                case "5":
                    System.out.println("DIGITE A RAÇA:");
                    filterList = filterSearchByBreed(input.nextLine(), filterList);
                    break;
                case "6":
                    System.out.println("DIGITE O TIPO [CAT/DOG]:");
                    filterList = filterSearchByTypePet(input.nextLine(), filterList);
                    break;
                default:
                    System.err.println("OPÇÃO INVÁLIDA. Tente novamente.");
                    break;
            }
        }

        System.out.println("\n--- RESULTADO FINAL DA BUSCA ---");
        formatListPets(filterList);
    }


    private void searchMenu() {
        System.out.println("1. Nome ou sobrenome\n" +
                "2. Sexo\n" +
                "3. Idade\n" +
                "4. Peso\n" +
                "5. Raça\n" +
                "6. Tipo" );
        System.out.println("0. Finalizar busca e mostrar resultados");
        System.out.println("\nESCOLHA UM CRITÉRIO PARA ADICIONAR (ou 0 para sair):");
    }

    private List<PetDto> filterSearchByName(String search, List<PetDto> petModelList) {
        String finalSearch = search.toUpperCase();
        return petModelList.stream().filter(pet -> pet.getFirstName().contains(finalSearch) || pet.getLastName().contains(finalSearch)).toList();
    }

    private List<PetDto> filterSearchByAge(String search, List<PetDto> petModelList) {
        Integer finalSearch = Integer.parseInt(search);
        return petModelList.stream().filter(pet -> pet.getAge().equals(finalSearch)).toList();
    }

    private List<PetDto> filterSearchByWeight(String search, List<PetDto> petModelList) {
        BigDecimal finalSearch = BigDecimal.valueOf(Double.parseDouble(search));
        return petModelList.stream().filter(pet -> pet.getWeight().compareTo(finalSearch) == 0).collect(Collectors.toList());
    }

    private List<PetDto> filterSearchByGender(String search, List<PetDto> petModelList) {
        String finalSearch = search.toUpperCase();
        return petModelList.stream().filter(pet -> pet.getGender().equals(Gender.valueOf(finalSearch))).toList();
    }

    private List<PetDto> filterSearchByBreed(String search, List<PetDto> petModelList) {
        String finalSearch = search.toUpperCase();
        return petModelList.stream().filter(pet -> pet.getBreed().contains(finalSearch)).toList();
    }

    private List<PetDto> filterSearchByTypePet(String search, List<PetDto> petModelList) {
        String finalSearch = search.toUpperCase();
        return petModelList.stream().filter(pet -> pet.getTypePet().equals(TypePet.valueOf(finalSearch))).toList();
    }


    public void formatListPets(List<PetDto> filterList) {
        if (filterList.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios selecionados.");
            return;
        }

        int contador = 1;
        for (PetDto pet : filterList) {
            String formattedPet = String.format(
                    "%d. %s - %s - %s - %s, - %d years - %s.kg - %s",
                    contador++,
                    pet.getFirstName(),
                    pet.getLastName(),
                    pet.getTypePet(),
                    pet.getGender(),
                    pet.getAge(),
                    pet.getWeight(),
                    pet.getBreed()
            );
            System.out.println(formattedPet);
        }
    }


}
