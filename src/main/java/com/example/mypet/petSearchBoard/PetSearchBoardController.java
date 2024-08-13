package com.example.mypet.petSearchBoard;

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

@RequestMapping("/board/pet-search")
@RestController
@RequiredArgsConstructor
@Tag(name = "pet search", description = "<강아지를 찾아요> 게시판 API")
public class PetSearchBoardController {
    private final PetSearchBoardService petSearchBoardService;

    @GetMapping("/{petSearchBoardId}")
    @Operation(summary = "<강아지를 찾아요> 게시판 상세 조회", description = "강아지를 찾아요 게시판을 상세 조회합니다.")
    public ResponseEntity<PetSearchBoardResponseDto> getDetailOwnerSearchBoard(@PathVariable String petSearchBoardId) {
        return ResponseEntity.ok(petSearchBoardService.getDetailPetSearchBoard(petSearchBoardId));
    }

    @GetMapping
    @Operation(summary = "<강아지를 찾아요> 게시판 전체 조회", description = "강아지를 찾아요 게시판을 전체 조회합니다.")
    public ResponseEntity<Page<PetSearchBoardResponseDto>> getAllBoards(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Page<PetSearchBoardResponseDto> boards = petSearchBoardService.getPageablePetSearchBoard(PageRequest.of(page, size));
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/user")
    @Operation(summary = "<강아지를 찾아요> 사용자가 작성한 게시글", description = "사용자가 작성한 게시글을 List형태로 반환합니다.")
    public ResponseEntity<List<PetSearchBoardResponseDto>> getUserBoards(@AuthenticationPrincipal User user) {
        var boards = petSearchBoardService.getUserBoards(user.getUsername());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/user/except")
    @Operation(summary = "<강아지를 찾아요> 사용자가 작성한 글을 제외하고 보여줍니다.", description = "사용자가 작성한 글을 제외하고 보여줍니다..")
    public ResponseEntity<Page<PetSearchBoardResponseDto>> getBoardsExceptUsers(@AuthenticationPrincipal User user,
                    @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        Page<PetSearchBoardResponseDto> boards = petSearchBoardService.getPageablePetSearchBoardExceptUser(user.getUsername(), PageRequest.of(page, size));
        return ResponseEntity.ok(boards);
    }

    @PostMapping()
    @Operation(summary = "<강아지를 찾아요> 게시판 등록", description = "<강아지를 찾습니다> 게시판에 등록을 합니다.")
    public ResponseEntity<PetSearchBoardResponseDto> createOwnerSearchBoard(@AuthenticationPrincipal User user, @RequestBody PetSearchBoardRequestDto requestDto) throws IOException {
        var res = petSearchBoardService.createPetSearchBoard(user.getUsername(), requestDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
