package com.example.mypet.pet.service;

import com.example.mypet.pet.domain.dto.PetDto;
import com.example.mypet.security.domain.users.Users;

import java.util.List;
import java.util.Optional;

public interface PetService {

    public PetDto savePet(Users user, PetDto petDto);

    public Optional<PetDto> getPetById(Users user, String petId);

    public List<PetDto> getPetsByUserId(Users user);

    public PetDto UpdatePet(Users user, String petId, PetDto petDto);

    public void deletePet(Users user,String petId);
}
