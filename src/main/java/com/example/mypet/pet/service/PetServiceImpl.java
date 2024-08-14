package com.example.mypet.pet.service;

import com.amazonaws.services.directory.model.ServiceException;
import com.example.mypet.global.ex.PetNotFoundException;
import com.example.mypet.global.ex.UserNotFoundException;
import com.example.mypet.pet.PetUtil;
import com.example.mypet.pet.domain.PetMapper;
import com.example.mypet.pet.domain.dto.PetRequestDto;
import com.example.mypet.pet.domain.dto.PetResponseDto;
import com.example.mypet.pet.domain.entity.Pets;
import com.example.mypet.pet.repository.PetRepository;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import com.example.mypet.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;



@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final PetUtil petUtil;

    @Transactional
    @Override
    public void savePet(String userId,@Valid PetRequestDto petRequestDto) throws IOException {

        try {
            Users user = petUtil.findUserById(userId);
            if (user == null) {
                throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
            }

            Pets pets = petMapper.toPetEntity(petRequestDto);
            Pets savedPets = petRepository.save(pets);

            // 사용자의 펫 리스트에 새로 저장된 펫 추가
            user.getPets().add(savedPets);

            petUtil.saveUser(user);
        } catch (UserNotFoundException | PetNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("펫 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

//    @Override
//    @Transactional(readOnly = true)
//    public Optional<Pets> getPetById(String userId, String petId) {
//        try {
//            Users userById = petUtil.findUserById(userId);
//            if (userById == null) {
//                throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
//            }
//
//            Pets pets = petUtil.findPetById(userById, petId);
//            if (pets == null) {
//                throw new PetNotFoundException("펫을 찾을 수 없습니다.");
//            }
//
//            return Optional.of(pets);
//        } catch (UserNotFoundException | PetNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new ServiceException("펫 조회 중 오류가 발생했습니다: " + e.getMessage());
//        }
//    }

    @Override
    @Transactional(readOnly = true)
    public List<PetResponseDto> getPetsByUser(String userId) {
        try {
            Users userById = petUtil.findUserById(userId);
            if (userById == null) {
                throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
            }
            return userById.getPets().stream().map(petMapper::toPetResponseDto).toList();
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("사용자의 펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // todo: pet update할 때 index로 update
//    @Transactional
//    public void updatePet(String userId, PetRequestDto petRequestDto) {
//        try {
//            Users userById = petUtil.findUserById(userId);
//            if (userById == null) {
//                throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
//            }
//
//            Pets existingPets = petUtil.findPetById(userById, petId);
//            if (existingPets == null) {
//                throw new PetNotFoundException("펫을 찾을 수 없습니다.");
//            }
//
//            existingPets.updateFromDto(petRequestDto);
//            petRepository.save(existingPets);
//        } catch (UserNotFoundException | PetNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new ServiceException("펫 업데이트 중 오류가 발생했습니다: " + e.getMessage());
//        }
//    }

    // todo: pet 지울 때 index로 지우기
//    @Override
//    @Transactional
//    public void deletePet(String userId, String petId) {
//        try {
//            Users userById = petUtil.findUserById(userId);
//            if (userById == null) {
//                throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
//            }
//
//            Pets pets = petUtil.findPetById(userById, petId);
//            if (pets == null) {
//                throw new PetNotFoundException("펫을 찾을 수 없습니다.");
//            }
//
//            userById.getPets().remove(pets);
//            petUtil.saveUser(userById);
//            petRepository.delete(pets);
//        } catch (UserNotFoundException | PetNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new ServiceException("펫 삭제 중 오류가 발생했습니다: " + e.getMessage());
//        }
//    }

    public MultipartFile convertBase64ToMultipartFile(String base64String, String fileName) throws IOException {
        // Base64 문자열을 디코딩하여 바이트 배열로 변환
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // MultipartFile 생성
        return new MockMultipartFile(
                fileName,                          // 파일 이름
                fileName,                          // 원래 파일 이름
                "application/octet-stream",        // MIME 타입 (필요에 따라 변경 가능)
                new ByteArrayInputStream(decodedBytes) // 바이트 배열을 InputStream으로 변환
        );
    }
}
