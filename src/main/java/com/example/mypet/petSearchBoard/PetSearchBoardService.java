package com.example.mypet.petSearchBoard;

import com.example.mypet.exception.UnAuthorizedException;
import com.example.mypet.global.ex.ResourceNotFoundException;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PetSearchBoardService {
    private final UsersRepository usersRepository;
    private final PetSearchBoardRepository petSearchBoardRepository;
    private final PetSearchBoardMapper petSearchBoardMapper;

    @Transactional
    public PetSearchBoardResponseDto createPetSearchBoard(String userId, @Valid PetSearchBoardRequestDto requestDto) throws IOException {
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        var petSearchBoard = petSearchBoardMapper.toPetSearchBoard(requestDto, user);
        return petSearchBoardMapper.toPetSearchBoardResponseDto(petSearchBoardRepository.save(petSearchBoard));
    }

    @Transactional(readOnly = true)
    public PetSearchBoardResponseDto getDetailPetSearchBoard(String petSearchBoardId) {

        var detailedPetBoard = petSearchBoardRepository.findById(petSearchBoardId).orElseThrow(
                () -> new IllegalArgumentException("Board not found")
        );
        return petSearchBoardMapper.toPetSearchBoardResponseDto(detailedPetBoard);
    }

    @Transactional(readOnly = true)
    public Page<PetSearchBoardResponseDto> getPageablePetSearchBoard(Pageable pageable) {
        var petSearchBoardPage = petSearchBoardRepository.findAll(pageable);
        var boards = petSearchBoardPage.map(petSearchBoardMapper::toPetSearchBoardResponseDto).stream().toList();
        return new PageImpl<>(boards, pageable, petSearchBoardPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<PetSearchBoardResponseDto> getUserBoards(String userId) {
        return petSearchBoardRepository.findByUser_Id(userId).stream().map(petSearchBoardMapper::toPetSearchBoardResponseDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<PetSearchBoardResponseDto> getPageablePetSearchBoardExceptUser(String userId,Pageable pageable) {
        var petSearchBoardExceptUserPage = petSearchBoardRepository.findByUser_IdNot(new ObjectId(userId), pageable);

        var boards = petSearchBoardExceptUserPage.map(petSearchBoardMapper::toPetSearchBoardResponseDto).stream().toList();
        return new PageImpl<>(boards, pageable, petSearchBoardExceptUserPage.getTotalElements());
    }

    @Transactional
    public void deletePetSearchBoard(String userId, String deletePetSearchBoardId) {
        var petSearchBoard = petSearchBoardRepository.findById(deletePetSearchBoardId).orElseThrow(
                () -> new ResourceNotFoundException("Board not found with id: " + deletePetSearchBoardId)
        );
        if (!petSearchBoard.getUser().getId().equals(userId)){
            throw new UnAuthorizedException("User is not authorized to delete this board");
        }
        petSearchBoardRepository.delete(petSearchBoard);
    }
}
