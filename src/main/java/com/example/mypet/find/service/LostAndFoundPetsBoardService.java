package com.example.mypet.find.service;

import com.example.mypet.find.domain.dto.request.OwnerSearchBoardRequestDto;
import com.example.mypet.find.domain.dto.request.PetLostBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LostAndFoundPetsBoardService {

  void createPetLostBoard(String userId, PetLostBoardRequestDto requestDto);
  void createOwnerFoundBoard(OwnerSearchBoardRequestDto requestDto);
  Page<LostAndFoundPetsBoardResponseDto> getAllBoards(PostType postType, Pageable pageable); // 페이징 처리된 게시판 목록 반환
  LostAndFoundPetsBoardDetailResponseDto getBoardDetailById(String boardId); // 게시판 상세 정보 반환

  void deletePetLostBoard(String boardId);

  void deleteOwnerFoundBoard(String boardId);
}
