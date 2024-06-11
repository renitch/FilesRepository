package com.demo.fileUpload.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.fileUpload.model.FileInfo;

public interface FileInfoRepository extends CrudRepository<FileInfo, String> {
    List<FileInfo> findAllByOrderByUploadDateDesc();
}
