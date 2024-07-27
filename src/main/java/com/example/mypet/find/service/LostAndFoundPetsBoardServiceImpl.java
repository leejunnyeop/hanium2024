package com.example.mypet.find.service;

import com.example.mypet.find.repository.LostAndFoundPetsBoardRepository;
import com.example.mypet.find.domain.LostAndFoundPetsBoardMapper;
import com.example.mypet.find.domain.dto.request.OwnerFoundBoardRequestDto;
import com.example.mypet.find.domain.dto.request.PetLostBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.pet.domain.entity.Pets;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LostAndFoundPetsBoardServiceImpl implements LostAndFoundPetsBoardService{

    private final LostAndFoundPetsBoardRepository boardRepository;
    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public LostAndFoundPetsBoardResponseDto createPetLostBoard(String userId, PetLostBoardRequestDto requestDto) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ID가 " + userId + "인 사용자를 찾을 수 없습니다."));

        if (users.getPets().isEmpty()) {
            throw new RuntimeException("사용자에게 등록된 강아지가 없습니다.");
        }

        Pets pet = users.getPets().get(0); // 첫 번째 강아지 선택 (또는 다른 로직으로 선택 가능)

        LostAndFoundPetsBoard board = LostAndFoundPetsBoard.builder()
                .content(requestDto.getContent())
                .location(requestDto.getLocation())
                .missingDateTime(requestDto.getDateTime())
                .reporterName(requestDto.getReporterName())
                .reporterContact(requestDto.getReporterContact())
                .postType(PostType.PET)
                .petsId(pet.getId())
                .build();

        LostAndFoundPetsBoard createdBoard = boardRepository.save(board);
        return LostAndFoundPetsBoardMapper.toPetLostBoardResponseDto(createdBoard, pet);
    }

    @Override
    @Transactional
    public LostAndFoundPetsBoardResponseDto createOwnerFoundBoard(OwnerFoundBoardRequestDto requestDto) {
        LostAndFoundPetsBoard board = LostAndFoundPetsBoard.builder()
                .content(requestDto.getContent())
                .location(requestDto.getLocation())
                .missingDateTime(requestDto.getDateTime())
                .reporterName(requestDto.getReporterName())
                .reporterContact(requestDto.getReporterContact())
                .postType(PostType.OWNER)
                .build();

        LostAndFoundPetsBoard createdBoard = boardRepository.save(board);
        return LostAndFoundPetsBoardMapper.toOwnerFoundBoardResponseDto(createdBoard);
    }

    @Override
    public Page<LostAndFoundPetsBoardResponseDto> getAllBoards(PostType postType, Pageable pageable) {
        Page<LostAndFoundPetsBoard> boardsPage = boardRepository.findAllByPostType(postType, pageable);
        List<LostAndFoundPetsBoardResponseDto> boards = boardsPage.getContent().stream().map(board -> {
            if (postType == PostType.PET) {
                Pets pet = usersRepository.findById(board.getPetsId()).get().getPets().stream()
                        .filter(p -> p.getId().equals(board.getPetsId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("ID가 " + board.getPetsId() + "인 강아지를 찾을 수 없습니다."));
                return LostAndFoundPetsBoardMapper.toPetLostBoardResponseDto(board, pet);
            } else {
                return LostAndFoundPetsBoardMapper.toOwnerFoundBoardResponseDto(board);
            }
        }).collect(Collectors.toList());
        return new PageImpl<>(boards, pageable, boardsPage.getTotalElements());
    }

    @Override
    public LostAndFoundPetsBoardDetailResponseDto getBoardDetailById(String boardId) {
        LostAndFoundPetsBoard board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("ID가 " + boardId + "인 게시글을 찾을 수 없습니다."));
        if (board.getPostType() == PostType.PET) {
            Pets pet = usersRepository.findById(board.getPetsId()).get().getPets().stream()
                    .filter(p -> p.getId().equals(board.getPetsId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("ID가 " + board.getPetsId() + "인 강아지를 찾을 수 없습니다."));
            return LostAndFoundPetsBoardMapper.toPetLostBoardDetailResponseDto(board, pet);
        } else {
            return LostAndFoundPetsBoardMapper.toOwnerFoundBoardDetailResponseDto(board);
        }
    }
}