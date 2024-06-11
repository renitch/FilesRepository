package com.demo.fileUpload.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.fileUpload.model.FileInfo;
import com.demo.fileUpload.model.HashTag;
import com.demo.fileUpload.repository.FileInfoRepository;
import com.demo.fileUpload.repository.HashTagRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;
    
    @Autowired
    private FileInfoRepository fileInfoRepository;
    
    @Autowired
    private HashTagRepository hashTagRepository;

    public String addFile(MultipartFile upload, List<String> hastTagsList) throws IOException {

        //define additional metadata
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        //store in database which returns the objectID
        ObjectId fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);
        
        List<HashTag> hashTags = new ArrayList<>();
        for (String hashTag : hastTagsList) {
            hashTags.addAll(hashTagRepository.findByValue(hashTag));
        }
        
        FileInfo fileInfo = 
                new FileInfo(
                        fileID.toString(), 
                        fileID,
                        upload.getOriginalFilename(),
                        new Date(),
                        upload.getSize(),
                        upload.getContentType(),
                        hashTags);

        fileInfoRepository.save(fileInfo);
        
        for (HashTag hashTag : hashTags) {
            hashTag.setFile(fileInfo);
        }

        //return as a string
        return fileID.toString();
    }

    public FileInfo downloadFile(String id) throws IOException {

        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );
        FileInfo fileInfo = fileInfoRepository.findById(id).get();
        fileInfo.setBytes(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
        
        return fileInfo;
    }
    
    public List<FileInfo> getAllFiles() throws IOException {

        return (List<FileInfo>) fileInfoRepository.findAllByOrderByUploadDateDesc();
    }

    public void deleteFile(String id) throws IOException {
        template.delete(new Query(Criteria.where("_id").is(id)));
        fileInfoRepository.deleteById(id);
    }
}
