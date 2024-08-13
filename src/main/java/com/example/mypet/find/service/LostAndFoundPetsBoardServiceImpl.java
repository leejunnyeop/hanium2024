package com.example.mypet.find.service;

import com.example.mypet.find.repository.LostAndFoundPetsBoardRepository;
import com.example.mypet.find.domain.LostAndFoundPetsBoardMapper;
import com.example.mypet.ownerSearchBoard.OwnerSearchBoardRequestDto;
import com.example.mypet.find.domain.dto.request.PetLostBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.global.ex.ServiceException;
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
    public void createPetLostBoard(String userId, PetLostBoardRequestDto requestDto) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ID가 " + userId + "인 사용자를 찾을 수 없습니다."));
        LostAndFoundPetsBoard board;

//         board = LostAndFoundPetsBoardMapper.toLostAndFoundPetsBoardManual(requestDto);

//        board.calculateAge();
//        LostAndFoundPetsBoard createdBoard = boardRepository.save(board);
    }

    @Override
    @Transactional
    public void createOwnerFoundBoard(OwnerSearchBoardRequestDto requestDto) {
//        LostAndFoundPetsBoard board = LostAndFoundPetsBoardMapper.toOwnerFoundBoard(requestDto);
//        LostAndFoundPetsBoard createdBoard = boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<LostAndFoundPetsBoardResponseDto> getAllBoards(PostType postType, Pageable pageable) {
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

    @Transactional(readOnly = true)
    public LostAndFoundPetsBoardDetailResponseDto getBoardDetailById(String boardId) {
//        LostAndFoundPetsBoard board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new ServiceException("ID가 " + boardId + "인 게시글을 찾을 수 없습니다."));
//        if (board.getPostType() == PostType.PET) {
//            return LostAndFoundPetsBoardMapper.toLostAndFoundPetsBoardDetailResponseDto(board);
//        } else {
//            return LostAndFoundPetsBoardMapper.toOwnerFoundBoardDetailResponseDto(board);
//        }
        return null;
    }

    @Override
    @Transactional
    public void deletePetLostBoard(String boardId) {
        LostAndFoundPetsBoard board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ServiceException("ID가 " + boardId + "인 강아지 찾기 게시글을 찾을 수 없습니다."));
        if (board.getPostType() != PostType.PET) {
            throw new ServiceException("해당 게시글은 강아지 찾기 게시글이 아닙니다.");
        }
        boardRepository.delete(board);
    }

    @Override
    @Transactional
    public void deleteOwnerFoundBoard(String boardId) {
        LostAndFoundPetsBoard board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ServiceException("ID가 " + boardId + "인 주인 찾기 게시글을 찾을 수 없습니다."));
        if (board.getPostType() != PostType.OWNER) {
            throw new ServiceException("해당 게시글은 주인 찾기 게시글이 아닙니다.");
        }
        boardRepository.delete(board);
    }


}
