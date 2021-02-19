package com.jedreck.testdfs.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.jedreck.testdfs.config.MyFastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class UploadImageService {
    @Autowired
    private MyFastFileStorageClient fastFileStorageClient;

    public String uploadImg(MultipartFile multipartFile) throws IOException {
        String originalFilename = Optional.ofNullable(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))
                .orElse("").toLowerCase().trim();
        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(multipartFile.getInputStream()
                , multipartFile.getSize()
                , originalFilename, null);
        return storePath.getFullPath();
    }
}
