package com.example.mypet.find.service;

import com.example.mypet.find.domain.dto.request.OwnerFoundBoardRequestDto;
import com.example.mypet.find.domain.dto.request.PetLostBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LostAndFoundPetsBoardService {

    LostAndFoundPetsBoardResponseDto createPetLostBoard(String userId, PetLostBoardRequestDto requestDto);
    LostAndFoundPetsBoardResponseDto createOwnerFoundBoard(OwnerFoundBoardRequestDto requestDto);
    Page<LostAndFoundPetsBoardResponseDto> getAllBoards(PostType postType, Pageable pageable); // 페이징 처리된 게시판 목록 반환
    LostAndFoundPetsBoardDetailResponseDto getBoardDetailById(String boardId); // 게시판 상세 정보 반환
}
