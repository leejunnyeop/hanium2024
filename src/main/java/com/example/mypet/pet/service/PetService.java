package com.example.mypet.pet.service;

import com.example.mypet.pet.domain.dto.PetDto;

import java.util.List;
import java.util.Optional;

public interface PetService {

    public PetDto savePet(String userId, PetDto petDto);

    public Optional<PetDto> getPetById(String userId, String petId);

    public List<PetDto> getPetsByUser(String userId);

    public PetDto updatePet(String userId, String petId, PetDto petDto);

    public void deletePet(String userId,String petId);


}
