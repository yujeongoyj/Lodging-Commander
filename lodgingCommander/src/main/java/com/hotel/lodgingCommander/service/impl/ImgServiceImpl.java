package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.entity.Img;
import com.hotel.lodgingCommander.model.repository.ImgRepository;
import com.hotel.lodgingCommander.service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService {

    private final ImgRepository imgRepository;

    @Transactional
    public Boolean save(MultipartFile image) throws IOException {

        var uploadDir = "src/main/resources/static/uploads";
        var uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        var fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        var filePath = uploadPath.resolve(fileName);

        Files.copy(image.getInputStream(), filePath);

        var img = Img.builder()
                .path("http://localhost:8080/uploads/" + fileName)
                .build();
        return imgRepository.save(img) != null ? true : false;
    }

}
