package com.example.mypet.pet.service;

import com.example.mypet.pet.domain.dto.PetDto;
import com.example.mypet.security.domain.users.Users;

import java.util.List;
import java.util.Optional;

public interface PetService {

    public PetDto savePet(Users user, PetDto petDto);

    public Optional<PetDto> getPetById(Users user, String petId);

    public List<PetDto> getPetsByUser(Users user);

    public PetDto updatePet(Users user, String petId, PetDto petDto);

    public void deletePet(Users user,String petId);


}
