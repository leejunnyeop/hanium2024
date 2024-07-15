package com.example.mypet.pet;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.example.mypet.pet.domain.entity.Pet;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetUtil {

    private final UsersRepository usersRepository;

    public Users findUserById(String userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 " + userId + "인 사용자를 찾을 수 없습니다."));
    }

    public Pet findPetById(Users user, String petId) {
        return user.getPets().stream()
                .filter(p -> p.getId().equals(petId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("ID가 " + petId + "인 펫을 찾을 수 없습니다."));
    }

    public void saveUser(Users user) {
        usersRepository.save(user);
    }
}
