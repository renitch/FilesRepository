package com.demo.fileUpload.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FileInfo {

    @Id
    private String id;
    
    private ObjectId file;

    private String filename;
    private Date uploadDate;
    private long fileSize;
    private String fileType;
    private byte[] bytes;

    @DBRef
    private List<HashTag> hashTags;

    public FileInfo() {
        
    }
    
    public FileInfo(String id, ObjectId file, List<HashTag> hashTags) {
        super();
        this.id = id;
        this.file = file;
        this.hashTags = hashTags;
    }
    
    public FileInfo(String id, ObjectId file, String filename, 
            Date uploadDate, long fileSize,
            String fileType, List<HashTag> hashTags) {
        super();
        this.id = id;
        this.file = file;
        this.filename = filename;
        this.uploadDate = uploadDate;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.hashTags = hashTags;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectId getFile() {
        return file;
    }

    public void setFile(ObjectId file) {
        this.file = file;
    }

    public List<HashTag> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<HashTag> hashTags) {
        this.hashTags = hashTags;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}
