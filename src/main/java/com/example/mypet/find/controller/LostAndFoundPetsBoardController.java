package com.example.mypet.find.controller;

import com.amazonaws.waiters.HttpSuccessStatusAcceptor;
import com.example.mypet.find.domain.dto.request.OwnerSearchBoardRequestDto;
import com.example.mypet.find.domain.dto.request.PetLostBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.dto.response.OwnerSearchBoardResponseDto;
import com.example.mypet.find.domain.entity.OwnerSearchBoard;
import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.find.service.LostAndFoundPetsBoardService;
import com.example.mypet.find.service.OwnerSearchBoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "lost and found", description = "실종 신고 게시판 API" )
public class LostAndFoundPetsBoardController {

    private final LostAndFoundPetsBoardService boardService;
    private final OwnerSearchBoardService ownerSearchBoardService;

    // todo 고쳐야 할게 매우 많다....

    /**
     * <강아지를 찾아요> 게시판 분리
     */
    @PostMapping("/pet-lost")
    @Operation(summary = "<강아지를 찾아요> 게시판 등록", description = "펫 실종 신고를 합니다.")
    public ResponseEntity<String> createPetLostBoard(@AuthenticationPrincipal User user,
                                                                               @Valid @RequestBody PetLostBoardRequestDto requestDto) {
        String userId = user.getUsername(); // 사용자 ID
        boardService.createPetLostBoard(userId, requestDto);
        return ResponseEntity.ok("분실 신고가 접수 되었습니다");
    }

    /**
     * 주인을 찾아요 todo controller 분리
     */
    @GetMapping("/owner-search/{ownerSearchBoardId}")
    @Operation(summary = "<주인을 찾아요> 게시판 상세 조회", description = "주인을 찾아요 게시판을 상세 조회합니다.")
    public ResponseEntity<OwnerSearchBoard> getDetailOwnerSearchBoard(@PathVariable String ownerSearchBoardId) {
        return ResponseEntity.ok(ownerSearchBoardService.getDetailOwnerSearchBoard(ownerSearchBoardId));
    }

    @GetMapping("/owner-search")
    @Operation(summary = "<주인을 찾아요> 게시판 전체 조회", description = "주인을 찾아요 게시판을 전체 조회합니다.")
    public ResponseEntity<Page<OwnerSearchBoardResponseDto>> getAllBoards(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Page<OwnerSearchBoardResponseDto> boards = ownerSearchBoardService.getPageableOwnerSearchBoard(PageRequest.of(page, size));
        return ResponseEntity.ok(boards);
    }

    @PostMapping("/owner-search")
    @Operation(summary = "<주인을 찾아요> 게시판 등록", description = "<주인을 찾습니다> 게시판에 등록을 합니다.")
    public ResponseEntity<OwnerSearchBoard> createOwnerSearchBoard(@AuthenticationPrincipal User user, @RequestBody OwnerSearchBoardRequestDto requestDto) throws IOException {
        var res = ownerSearchBoardService.createOwnerSearchBoard(user.getUsername(), requestDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    // -----------------------------------------

    @GetMapping
    @Operation(summary = "게시판 보여주기", description = "postType에 따라 게시판 종류가 달라집니다. page, size를 입력 받아야 합니다")
    public ResponseEntity<Page<LostAndFoundPetsBoardResponseDto>> getAllBoards(
            @RequestParam(name = "postType") PostType postType,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Page<LostAndFoundPetsBoardResponseDto> boards = boardService.getAllBoards(postType, PageRequest.of(page, size));
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{boardId}")
    @Operation(summary = "상세 게시판 보여주기", description = "")
    public ResponseEntity<LostAndFoundPetsBoardDetailResponseDto> getBoardDetailById(@PathVariable(name = "boardId") String boardId) {
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
