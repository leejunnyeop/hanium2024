package com.example.mypet.pet.service;

import com.example.mypet.pet.domain.dto.PetDto;

import java.util.List;
import java.util.Optional;

public interface PetService {

    PetDto savePet(String userId, PetDto petDto);

    Optional<PetDto> getPetById(String userId, String petId);

    List<PetDto> getPetsByUser(String userId);

    PetDto updatePet(String userId, String petId, PetDto petDto);

    void deletePet(String userId, String petId);


}
