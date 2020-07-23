package com.example.webservice.service.posts;

import com.example.webservice.web.domain.files.FilesRepository;
import com.example.webservice.web.domain.files.UploadFiles;
import com.example.webservice.web.dto.FilesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor // 생성자로 빈 주입
@Service
public class FilesService {
    private final FilesRepository filesRepository;

    // 파일 업로드
    @Transactional
    public ResponseEntity save(Long id, MultipartFile[] files) {
        try{
            List<UploadFiles> list = Arrays.stream(files).map(file -> {
                String fileOriName = file.getOriginalFilename();
                String orgFileExtension = fileOriName.substring(fileOriName.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + orgFileExtension;
                String fileType = file.getContentType();

                System.out.println("---------file start--------");
                System.out.println(fileOriName);
                System.out.println(fileName);
                System.out.println(fileType);
                System.out.println("-----------------");

                UploadFiles uploadfiles = UploadFiles
                        .builder()
                        .boardId(id)
                        .fileName(fileName)
                        .fileOriName(fileOriName)
                        .fileType(fileType)
                        .build();

                return uploadfiles;
            }).collect(Collectors.toList());

            for(UploadFiles file : list) {
                filesRepository.save(file);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 특정 게시글에 업로드 된 파일 불러오기
    public List<FilesResponseDto> getFilesList(Long boardId) {
        return filesRepository.findByBoardId(boardId).stream()
                .map(entity -> new FilesResponseDto(entity))
                .collect(Collectors.toList());
    }
}
