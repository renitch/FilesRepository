package com.demo.fileUpload.controller;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.fileUpload.model.HashTag;
import com.demo.fileUpload.service.HashTagService;

@Controller
@CrossOrigin("*")
@RequestMapping("hashtag")
public class HashTagsController {

    @Autowired
    private HashTagService hashTagService;

    @GetMapping("/")
    public ResponseEntity<List<HashTag>> getAllHashTags() throws IOException {
        System.out.println("Getting list of all hash tags...");
        List<HashTag> hashTags = hashTagService.getAllHashTags();

        return new ResponseEntity<>(hashTags, HttpStatus.OK);
    }
    
    @PostMapping("/add/")
    public String add(@RequestParam("hashTag") String hashTag) throws IOException {
        System.out.println("Adding new hash tag: " + hashTag);
        hashTagService.addNewHashTag(hashTag);
        return "redirect:/file/";
    }
}
