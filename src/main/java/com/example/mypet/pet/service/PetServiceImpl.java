package com.example.mypet.pet.service;

import com.amazonaws.services.directory.model.ServiceException;
import com.example.mypet.pet.PetUtil;
import com.example.mypet.pet.domain.PetMapper;
import com.example.mypet.pet.domain.dto.PetDto;
import com.example.mypet.pet.domain.entity.Pets;
import com.example.mypet.pet.repository.PetRepository;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
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
            Pets pets = PetMapper.toEntity(petDto);
            Pets savedPets = petRepository.save(pets);

            Users userById = petUtil.findUserById(userId);
            log.info("유저 : " + userById.getEmail());
            log.info("유저 " + userById.getPets());

            // 사용자의 펫 리스트에 새로 저장된 펫 추가
             userById.getPets().add(savedPets);

             log.info("사용자 펫 리스트에 새로 추가 userById : " + userById.getPets());

            petUtil.saveUser(userById);
            return PetMapper.toDto(savedPets);
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
            Pets pets = petUtil.findPetById(userById, petId);
            return Optional.of(PetMapper.toDto(pets));
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
            log.info("전체 조회 하기 : " + userById.getPets());
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
            Pets existingPets = petUtil.findPetById(userById, petId);
            existingPets.updateFromDto(petDto);
            Pets updatedPets = petRepository.save(existingPets);
            return PetMapper.toDto(updatedPets);
        } catch (Exception e) {
            throw new ServiceException("펫 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    // Pets 삭제
    @Override
    @Transactional
    public void deletePet(String userId, String petId) {
        try {
            Users userById = petUtil.findUserById(userId);
            Pets pets = petUtil.findPetById(userById, petId);
            userById.getPets().remove(pets);
            petUtil.saveUser(userById);
            petRepository.delete(pets);
        } catch (Exception e) {
            throw new ServiceException("펫 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


}
