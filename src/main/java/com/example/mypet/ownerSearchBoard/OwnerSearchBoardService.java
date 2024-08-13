package com.example.mypet.ownerSearchBoard;


import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OwnerSearchBoardService {
    private final UsersRepository usersRepository;
    private final OwnerSearchBoardMapper ownerSearchBoardMapper;
    private final OwnerSearchBoardRepository ownerSearchBoardRepository;

    @Transactional
    public OwnerSearchBoard createOwnerSearchBoard(String userId, @Valid OwnerSearchBoardRequestDto requestDto) throws IOException {
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        // mapper
        var ownerSearchBoard = ownerSearchBoardMapper.toOwnerSearchBoard(requestDto, user);
        return ownerSearchBoardRepository.save(ownerSearchBoard);
    }

    @Transactional(readOnly = true)
    public OwnerSearchBoardResponseDto getDetailOwnerSearchBoard(String ownerSearchBoardId) {

        var detailedOwnerBoard = ownerSearchBoardRepository.findById(ownerSearchBoardId).orElseThrow(
                () -> new IllegalArgumentException("Board not found")
        );
        return ownerSearchBoardMapper.toOwnerSearchBoardResponseDto(detailedOwnerBoard);
    }

    @Transactional(readOnly = true)
    public Page<OwnerSearchBoardResponseDto> getPageableOwnerSearchBoard(Pageable pageable) {
        var ownerSearchBoardPage = ownerSearchBoardRepository.findAll(pageable);
        var boards = ownerSearchBoardPage.map(ownerSearchBoardMapper::toOwnerSearchBoardResponseDto).stream().toList();
        return new PageImpl<>(boards, pageable, ownerSearchBoardPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<OwnerSearchBoardResponseDto> getUserBoards(String userId) {
        return ownerSearchBoardRepository.findByUser_Id(userId).stream().map(ownerSearchBoardMapper::toOwnerSearchBoardResponseDto).toList();
    }
}
