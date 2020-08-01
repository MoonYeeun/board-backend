package com.example.webservice.web;

import com.example.webservice.service.posts.FilesService;
import com.example.webservice.web.dto.FilesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FilesApiController {
    private final FilesService filesService;

    @Value("${file.upload.dir}")
    String uploadDir;

    // 파일 업로드
    @PostMapping("/api/upload")
    public ResponseEntity upload(@RequestParam("id") Long id, @RequestParam("files") MultipartFile[] files) {
        if(files.length == 0) return new ResponseEntity<>("There is no files", HttpStatus.OK);
        else return filesService.upload(id, files);
    }

    // 파일 다운로드
    @GetMapping("api/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id, HttpServletResponse response) {
        Resource resource = filesService.loadFile(id);
        FilesResponseDto fileInfo = filesService.getFileInfo(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileInfo.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // 첨부파일 목록
    @GetMapping("api/getFile/{boardId}")
    public List<FilesResponseDto> getFilesList(@PathVariable("boardId") Long boardId) {
        return filesService.getFilesList(boardId);
    }

    // 게시글 파일 전체 삭제
    @GetMapping("/api/deleteFile/{boardId}")
    public ResponseEntity delete(@PathVariable("boardId") Long boardId) {
        return filesService.delete(boardId);
    }
}
