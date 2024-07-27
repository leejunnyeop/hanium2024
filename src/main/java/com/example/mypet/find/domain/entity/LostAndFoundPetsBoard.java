package com.example.mypet.find.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "lost_and_found_pets_board")
public class LostAndFoundPetsBoard {

    @Id
    private String id;
    private String content; // 게시물 내용
    private String location; // 분실 장소
    private String missingDateTime; // 분실 일시
    private String reporterName; // 신고자 이름
    private String reporterContact; // 신고자 연락처
    private PostType postType; // 게시글 유형 (OWNER, PET)
    private String petsId; // Pet ID
}