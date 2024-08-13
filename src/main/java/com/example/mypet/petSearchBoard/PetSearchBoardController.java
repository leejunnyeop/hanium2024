package com.example.mypet.petSearchBoard;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/board/pet-search")
@RestController
@RequiredArgsConstructor
@Tag(name = "pet search", description = "<강아지를 찾아요> 게시판 API")
public class PetSearchBoardController {
}
