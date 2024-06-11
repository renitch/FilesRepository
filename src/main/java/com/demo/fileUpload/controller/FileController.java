package com.demo.fileUpload.controller;

import com.demo.fileUpload.model.FileInfo;
import com.demo.fileUpload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("*")
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("hashTags") String hashTags) throws IOException {
        System.out.println("Starting upload...");
        
        List<String> hashTagsList = (List<String>) Arrays.asList(hashTags.split("#"))
                .stream()
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toList());
        
        for (String str : hashTagsList) {
            System.out.println("Parsed hashTag: " + str);
        }
        
        return new ResponseEntity<>(fileService.addFile(file, hashTagsList), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        System.out.println("Downloading file with id: " + id);

        FileInfo loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getBytes()));
    }
    
    @GetMapping("/")
    public String index(Model model) throws IOException {
        System.out.println("Getting files...");
        List<FileInfo> videos = fileService.getAllFiles();

        model.addAttribute("videos", videos);
        return "index";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {
        System.out.println("Starting delete id..." + id);
        fileService.deleteFile(id);
        return "redirect:/file/";
    }
}
