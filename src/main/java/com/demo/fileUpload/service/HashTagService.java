package com.demo.fileUpload.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.fileUpload.model.HashTag;
import com.demo.fileUpload.repository.HashTagRepository;

@Service
public class HashTagService {

    @Autowired
    private HashTagRepository hashTagRepository;

    public List<HashTag> getAllHashTags() throws IOException {
        return hashTagRepository.findAll();
    }
    
    public void addNewHashTag(String value) {
        if (hashTagRepository.findByValue(value).isEmpty()) {
            HashTag hashTag = new HashTag(value);
            hashTagRepository.save(hashTag);
        }
    }
}
