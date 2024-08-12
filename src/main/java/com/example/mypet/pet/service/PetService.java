package com.example.mypet.pet.service;

import com.example.mypet.pet.domain.dto.PetRequestDto;
import com.example.mypet.pet.domain.dto.PetResponseDto;
import com.example.mypet.pet.domain.entity.Pets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PetService {

    void savePet(String userId, PetRequestDto petRequestDto) throws IOException;

    Optional<Pets> getPetById(String userId, String petId);

    List<Pets> getPetsByUser(String userId);

    void updatePet(String userId, String petId, PetRequestDto petRequestDto);

    void deletePet(String userId, String petId);

}
