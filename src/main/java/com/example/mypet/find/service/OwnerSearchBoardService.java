package com.example.mypet.find.service;


import com.example.mypet.find.domain.LostAndFoundPetsBoardMapper;
import com.example.mypet.find.domain.OwnerSearchBoardMapper;
import com.example.mypet.find.domain.dto.request.OwnerSearchBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import com.example.mypet.find.domain.entity.OwnerSearchBoard;
import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.find.repository.OwnerSearchBoardRepository;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OwnerSearchBoardService {
    private final UsersRepository usersRepository;
    private final OwnerSearchBoardMapper ownerSearchBoardMapper;
    private final OwnerSearchBoardRepository ownerSearchBoardRepository;

    @Transactional
    public OwnerSearchBoard createOwnerSearchBoard(String userId, @Valid OwnerSearchBoardRequestDto requestDto) throws IOException {
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        // mapper
        var ownerSearchBoard = ownerSearchBoardMapper.toOwnerSearchBoard(requestDto, user);
        return ownerSearchBoardRepository.save(ownerSearchBoard);
    }

    @Transactional(readOnly = true)
    public OwnerSearchBoard getDetailOwnerSearchBoard(String ownerSearchBoardId) {
        return ownerSearchBoardRepository.findById(ownerSearchBoardId).orElseThrow(
                () -> new IllegalArgumentException("Board not found")
        );
    }

    @Transactional(readOnly = true)
    public Page<OwnerSearchBoard> getAllBoards(PostType postType, Pageable pageable) {
        Page<LostAndFoundPetsBoard> boardsPage = boardRepository.findAllByPostType(postType, pageable);
        List<LostAndFoundPetsBoardResponseDto> boards = boardsPage.getContent().stream().map(board -> {
            if (postType == PostType.PET) {
                return LostAndFoundPetsBoardMapper.toPetLostBoardResponseDto(board);
            } else {
                return LostAndFoundPetsBoardMapper.toOwnerFoundBoardResponseDto(board);
            }
        }).collect(Collectors.toList());
        return new PageImpl<>(boards, pageable, boardsPage.getTotalElements());
    }
}
