package com.dev.sistemaCadastro.domain;

import com.dev.sistemaCadastro.dto.PetDto;
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
        int op = -1;
        while(op != 0){
            showMenu();
            try {
                op = Integer.parseInt(input.nextLine());

                switch (op){

                    case 1 -> saveNewPet();
                    case 0 -> System.out.println("Saindo do sistema");
                    default -> System.out.println("Digite um opção valida");
                }

            }catch (NumberFormatException e){
                System.out.println("ERRO: Digite um numero: " + e);
                op = -1;
            }
        }
        input.close();
    }


    public void showMenu(){
        System.out.println("---------------------------- MENU ----------------------------\n" +
                "1. Cadastrar um novo pet\n" +
                "2. Alterar os dados do pet cadastrado\n" +
                "3. Deletar um pet cadastrado\n" +
                "4. Listar todos os pets cadastrados\n" +
                "5. Listar pets por algum critério (idade, nome, raça)\n" +
                "0. Sair\n"+
                "ESCOLHA UMA OPÇÂO: ");
    }

    public void saveNewPet(){
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



}
