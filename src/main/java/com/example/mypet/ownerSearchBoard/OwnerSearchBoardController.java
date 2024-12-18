package com.example.mypet.ownerSearchBoard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/board/owner-search")
@RestController
@RequiredArgsConstructor
@Tag(name = "owner search", description = "<주인을 찾아요> 게시판 API")
public class OwnerSearchBoardController {
    private final OwnerSearchBoardService ownerSearchBoardService;

    @GetMapping("/{ownerSearchBoardId}")
    @Operation(summary = "<주인을 찾아요> 게시판 상세 조회", description = "주인을 찾아요 게시판을 상세 조회합니다.")
    public ResponseEntity<OwnerSearchBoardResponseDto> getDetailOwnerSearchBoard(@PathVariable String ownerSearchBoardId) {
        return ResponseEntity.ok(ownerSearchBoardService.getDetailOwnerSearchBoard(ownerSearchBoardId));
    }

    @GetMapping()
    @Operation(summary = "<주인을 찾아요> 게시판 전체 조회", description = "주인을 찾아요 게시판을 전체 조회합니다.")
    public ResponseEntity<Page<OwnerSearchBoardResponseDto>> getAllBoards(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Page<OwnerSearchBoardResponseDto> boards = ownerSearchBoardService.getPageableOwnerSearchBoard(PageRequest.of(page, size));
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/user")
    @Operation(summary = "<주인을 찾아요> 사용자가 작성한 게시글", description = "사용자가 작성한 게시글을 List형태로 반환합니다.")
    public ResponseEntity<List<OwnerSearchBoardResponseDto>> getUserBoards(@AuthenticationPrincipal User user){
        var boards = ownerSearchBoardService.getUserBoards(user.getUsername());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/user/except")
    @Operation(summary = "<주인을 찾아요> 사용자가 작성한 글을 제외하고 보여줍니다.", description = "사용자가 작성한 글을 제외하고 보여줍니다..")
    public ResponseEntity<Page<OwnerSearchBoardResponseDto>> getBoardsExceptUsers(@AuthenticationPrincipal User user,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Page<OwnerSearchBoardResponseDto> boards = ownerSearchBoardService.getPageableOwnerSearchBoardExceptUser(user.getUsername(), PageRequest.of(page, size));
        return ResponseEntity.ok(boards);
    }

    @PostMapping()
    @Operation(summary = "<주인을 찾아요> 게시판 등록", description = "<주인을 찾습니다> 게시판에 등록을 합니다.")
    public ResponseEntity<OwnerSearchBoardResponseDto> createOwnerSearchBoard(@AuthenticationPrincipal User user, @RequestBody OwnerSearchBoardRequestDto requestDto) throws IOException {
        var res = ownerSearchBoardService.createOwnerSearchBoard(user.getUsername(), requestDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping("/{ownerSearchBoardId}")
    @Operation(summary = "<주인을 찾아요> 게시판 삭제", description = "<주인을 찾습니다> 게시판에 삭제를 합니다.")
    public ResponseEntity<Void> deleteOwnerSearchBoard(@AuthenticationPrincipal User user, @PathVariable String ownerSearchBoardId) {
        ownerSearchBoardService.deleteOwnerSearchBoard(user.getUsername(), ownerSearchBoardId);
        return ResponseEntity.noContent().build();
    }

}
