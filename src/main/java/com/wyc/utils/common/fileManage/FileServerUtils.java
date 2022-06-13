package com.wyc.utils.common.fileManage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileServerUtils {

    private final String saveFilePath = System.getProperty("user.dir")+ File.separator+"files";

    public void upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (!Files.exists(Paths.get(saveFilePath))) {
            Files.createDirectory(Paths.get(saveFilePath));
        }
        File footerDir=new File(saveFilePath);
        if (!footerDir.exists()){
            boolean mkdirs = footerDir.mkdirs();
        }
    }

}
