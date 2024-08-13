package com.example.mypet.ownerSearchBoard;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
public class OwnerSearchBoardController {
    private final OwnerSearchBoardService ownerSearchBoardService;

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
}
