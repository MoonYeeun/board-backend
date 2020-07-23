package com.example.webservice.web;

import com.example.webservice.service.posts.FilesService;
import com.example.webservice.web.dto.FilesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FilesApiController {
    private final FilesService filesService;

    @Value("${file.upload.dir}")
    String uploadDir;

    // 파일업로드
    @PostMapping("/api/upload")
    public ResponseEntity save(@RequestParam("id") Long id, @RequestParam("files") MultipartFile[] files) {
        return filesService.save(id, files);
    }

    // 첨부파일 목록
    @GetMapping("api/filelist/{boardId}")
    public List<FilesResponseDto> getFilesList(@PathVariable("boardId") Long boardId) {
        return filesService.getFilesList(boardId);
    }
}
