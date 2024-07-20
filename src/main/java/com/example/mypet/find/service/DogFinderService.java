package com.example.mypet.find.service;

import com.example.mypet.find.domain.request.DogFinderRequest;
import com.example.mypet.find.domain.response.DogFinderDetailsResponse;
import com.example.mypet.find.domain.response.DogFinderResponse;

public interface DogFinderService {




    public DogFinderResponse dogGet(DogFinderRequest dogFinderRequest);

    //public DogFinderDetailsResponse dogDetailsGet();

   public void dogFinderSave(DogFinderRequest dogFinderRequest, String userId);

}
