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
    public PetDto savePet(Users user, PetDto petDto) {
        try {
            Pet pet = PetMapper.toEntity(petDto);
            Pet savedPet = petRepository.save(pet);


            user.getPets().add(savedPet);
            petUtil.saveUser(user);

            return PetMapper.toDto(savedPet);
        } catch (Exception e) {
            throw new ServiceException("펫 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ID로 PetDto 조회
    @Override
    @Transactional(readOnly = true)
    public Optional<PetDto> getPetById(Users user, String petId) {
        try {
            Pet pet = petUtil.findPetById(user, petId);
            return Optional.of(PetMapper.toDto(pet));
        } catch (Exception e) {
            throw new ServiceException("펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 사용자 ID로 사용자의 모든 PetDto 조회
    @Override
    @Transactional(readOnly = true)
    public List<PetDto> getPetsByUser(Users user) {
        try {
            return user.getPets().stream().map(PetMapper::toDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("사용자의 펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public PetDto updatePet(Users user, String petId, PetDto petDto) {
        try {
            Pet existingPet = petUtil.findPetById(user, petId);
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
    public void deletePet(Users user, String petId) {
        try {
            Pet pet = petUtil.findPetById(user, petId);
            user.getPets().remove(pet);
            petUtil.saveUser(user);
            petRepository.delete(pet);
        } catch (Exception e) {
            throw new ServiceException("펫 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


}
