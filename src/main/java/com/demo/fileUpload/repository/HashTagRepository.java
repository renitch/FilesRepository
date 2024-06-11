package com.demo.fileUpload.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.fileUpload.model.HashTag;

public interface HashTagRepository extends MongoRepository<HashTag, String> {
    List<HashTag> findByValue(String value);
}