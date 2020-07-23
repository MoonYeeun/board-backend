package com.example.webservice.service.posts;

import com.example.webservice.web.domain.files.FilesRepository;
import com.example.webservice.web.domain.files.UploadFiles;
import com.example.webservice.web.dto.FilesResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor // 생성자로 빈 주입
@Service
public class FilesService {
    private final FilesRepository filesRepository;
    private static final Logger logger = LoggerFactory.getLogger(FilesService.class);

    @Value("${file.upload.dir}")
    String fileDir;

    // 파일 업로드
    @Transactional
    public ResponseEntity upload(Long id, MultipartFile[] files) {
        try {
            for(MultipartFile file : files) {
                String fileOriName = file.getOriginalFilename();
                String orgFileExtension = fileOriName.substring(fileOriName.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + orgFileExtension;
                String fileType = file.getContentType();

                // 파일 저장
                File saveFile = new File(fileDir, fileName); // 저장할 폴더 이름, 저장할 파일 이름
                try {
                    file.transferTo(saveFile); // 요청 시점의 임시파일 로컬 파일 시스템에 저장
                } catch (Exception e) {
                    logger.info("Faild to upload attachment file");
                }

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

                // 파일 정보 저장
                filesRepository.save(uploadfiles);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 특정 게시글에 업로드 된 파일정보 불러오기
    public List<FilesResponseDto> getFilesList(Long boardId) {
        return filesRepository.findByBoardId(boardId).stream()
                .map(entity -> new FilesResponseDto(entity))
                .collect(Collectors.toList());
    }

    // 특정 게시글 파일 전체 삭제
    @Transactional
    public ResponseEntity delete(Long boardId) {
        try {
            List<UploadFiles> files = filesRepository.findByBoardId(boardId);

            for (UploadFiles f : files) {
                filesRepository.delete(f);

                String path = fileDir + f.getFileName(); // 삭제할 파일 경로와 파일명 지정
                File deleteFile = new File(path);

                if(deleteFile.exists()) deleteFile.delete();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }
}
