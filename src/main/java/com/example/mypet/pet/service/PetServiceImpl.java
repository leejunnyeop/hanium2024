package com.example.mypet.pet.service;

import com.amazonaws.services.directory.model.ServiceException;
import com.example.mypet.pet.PetUtil;
import com.example.mypet.pet.domain.PetMapper;
import com.example.mypet.pet.domain.dto.PetDto;
import com.example.mypet.pet.domain.entity.Pet;
import com.example.mypet.pet.repository.PetRepository;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final UsersRepository usersRepository;

    private final PetUtil petUtil;

    @Transactional
    @Override
    public PetDto savePet(String userId, PetDto petDto) {
        try {
            Pet pet = PetMapper.toEntity(petDto);
            Pet savedPet = petRepository.save(pet);

            Users userById = petUtil.findUserById(userId);
            petUtil.saveUser(userById);
            return PetMapper.toDto(savedPet);
        } catch (Exception e) {
            throw new ServiceException("펫 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ID로 PetDto 조회
    @Override
    @Transactional(readOnly = true)
    public Optional<PetDto> getPetById(String userId, String petId) {
        try {
            Users userById = petUtil.findUserById(userId);
            Pet pet = petUtil.findPetById(userById, petId);
            return Optional.of(PetMapper.toDto(pet));
        } catch (Exception e) {
            throw new ServiceException("펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 사용자 ID로 사용자의 모든 PetDto 조회
    @Override
    @Transactional(readOnly = true)
    public List<PetDto> getPetsByUser(String userId) {
        try {
            Users userById = petUtil.findUserById(userId);
            return userById.getPets().stream().map(PetMapper::toDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("사용자의 펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public PetDto updatePet(String userId, String petId, PetDto petDto) {
        try {
            Users userById = petUtil.findUserById(userId);
            Pet existingPet = petUtil.findPetById(userById, petId);
            existingPet.updateFromDto(petDto);
            Pet updatedPet = petRepository.save(existingPet);
            return PetMapper.toDto(updatedPet);
        } catch (Exception e) {
            throw new ServiceException("펫 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    // Pet 삭제
    @Override
    @Transactional
    public void deletePet(String userId, String petId) {
        try {
            Users userById = petUtil.findUserById(userId);
            Pet pet = petUtil.findPetById(userById, petId);
            userById.getPets().remove(pet);
            petUtil.saveUser(userById);
            petRepository.delete(pet);
        } catch (Exception e) {
            throw new ServiceException("펫 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


}
