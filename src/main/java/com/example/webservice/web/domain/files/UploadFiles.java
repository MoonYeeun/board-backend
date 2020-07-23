package com.example.webservice.web.domain.files;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity // 테이블과 링크될 클래스
public class UploadFiles {
    @Id // 해당 테이블의 PK
    // PK 생성규칙, type = identity 추가해야 auto increment 됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // file 번호

    @Column(nullable = false)
    private long boardId; // 어느 게시글의 파일인지 확인하기 위한 글번호

    @Column(columnDefinition = "TEXT", nullable = false)
    private String fileName; // 저장될 파일명
    private String fileOriName; // 원래 파일명
    private String fileType; // 파일 타입

    @Builder
    public UploadFiles(long boardId, String fileName, String fileOriName, String fileType) {
        this.boardId = boardId;
        this.fileName = fileName;
        this.fileOriName = fileOriName;
        this.fileType = fileType;
    }
}
