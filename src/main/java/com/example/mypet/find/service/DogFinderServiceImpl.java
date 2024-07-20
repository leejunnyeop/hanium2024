package com.example.mypet.find.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.mturk.model.ServiceException;
import com.example.mypet.find.domain.DogMapper;
import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import com.example.mypet.find.domain.request.DogFinderRequest;
import com.example.mypet.find.domain.response.DogFinderDetailsResponse;
import com.example.mypet.find.domain.response.DogFinderResponse;
import com.example.mypet.find.repository.DogFinderRepository;
import com.example.mypet.pet.PetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DogFinderServiceImpl implements DogFinderService {

    private final DogFinderRepository dogFinderRepository;
    private PetUtil petUtil;

    @Override
    public DogFinderResponse dogGet(DogFinderRequest dogFinderRequest) {
        return null;
    }


    @Override
    public void dogFinderSave(DogFinderRequest dogFinderRequest, String userId) {
        try {
            petUtil.findUserById(userId);
            LostAndFoundPetsBoard andFoundPetsBoard = DogMapper.toLostAndFoundPetsBoard(dogFinderRequest);
        }catch (ResourceNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new ServiceException("펫 등록이 실패 하였습니다. ");
        }

    }
}
