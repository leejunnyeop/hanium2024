package com.example.mypet.find.controller;

import com.example.mypet.find.domain.dto.request.OwnerFoundBoardRequestDto;
import com.example.mypet.find.domain.dto.request.PetLostBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.find.service.LostAndFoundPetsBoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class LostAndFoundPetsBoardController {

    private final LostAndFoundPetsBoardService boardService;

    @PostMapping("/create/pet-lost")
    @Operation(summary = "펫 실종 신고", description = "펫 실종 신고를 합니다.")
    public ResponseEntity<String> createPetLostBoard(@AuthenticationPrincipal User user,
                                                                               @Valid @RequestBody PetLostBoardRequestDto requestDto,
                                                                               @RequestParam boolean useExistingPet) {
        String userId = user.getUsername(); // 사용자 ID
         boardService.createPetLostBoard(userId, requestDto, useExistingPet);
        return ResponseEntity.ok("분실 신고가 접수 되었습니다");
    }


    @PostMapping("/create/owner-found")
    public ResponseEntity<String> createOwnerFoundBoard(@RequestBody OwnerFoundBoardRequestDto requestDto) {
        boardService.createOwnerFoundBoard(requestDto);
        return ResponseEntity.ok("주인을 찾습니다 가 접수 되었습니다");
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

    @Operation(summary = "강아지 찾기 게시글 삭제", description = "강아지 찾기 게시글을 삭제합니다.")
    @DeleteMapping("/pet-lost/{boardId}")
    public ResponseEntity<Void> deletePetLostBoard(@PathVariable String boardId) {
        boardService.deletePetLostBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "주인 찾기 게시글 삭제", description = "주인 찾기 게시글을 삭제합니다.")
    @DeleteMapping("/owner-found/{boardId}")
    public ResponseEntity<Void> deleteOwnerFoundBoard(@PathVariable String boardId) {
        boardService.deleteOwnerFoundBoard(boardId);
        return ResponseEntity.ok().build();
    }
}