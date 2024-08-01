package com.example.mypet.find.controller;

import com.example.mypet.find.domain.dto.request.OwnerFoundBoardRequestDto;
import com.example.mypet.find.domain.dto.request.PetLostBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.find.service.LostAndFoundPetsBoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Tag(name = "lost and found", description = "실종 신고 게시판 API" )
public class LostAndFoundPetsBoardController {

    private final LostAndFoundPetsBoardService boardService;

    @PostMapping("/create/pet-lost")
    public ResponseEntity<LostAndFoundPetsBoardResponseDto> createPetLostBoard(@AuthenticationPrincipal User user, @RequestBody PetLostBoardRequestDto requestDto) {
        LostAndFoundPetsBoardResponseDto createdBoard = boardService.createPetLostBoard(user.getUsername(), requestDto);
        return ResponseEntity.ok(createdBoard);
    }

    @PostMapping("/create/owner-found")
    public ResponseEntity<LostAndFoundPetsBoardResponseDto> createOwnerFoundBoard(@RequestBody OwnerFoundBoardRequestDto requestDto) {
        LostAndFoundPetsBoardResponseDto createdBoard = boardService.createOwnerFoundBoard(requestDto);
        return ResponseEntity.ok(createdBoard);
    }

    @GetMapping
    public ResponseEntity<Page<LostAndFoundPetsBoardResponseDto>> getAllBoards(
            @RequestParam(name = "postType") PostType postType,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Page<LostAndFoundPetsBoardResponseDto> boards = boardService.getAllBoards(postType, PageRequest.of(page, size));
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<LostAndFoundPetsBoardDetailResponseDto> getBoardDetailById(@PathVariable(name = "boardId") String boardId, @RequestParam(name = "postType") PostType postType) {
        LostAndFoundPetsBoardDetailResponseDto board = boardService.getBoardDetailById(boardId);
        return ResponseEntity.ok(board);
    }

}