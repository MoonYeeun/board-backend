package com.example.webservice.web.dto;

import com.example.webservice.web.domain.files.UploadFiles;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilesResponseDto {
    private Long id;
    private String fileOriName;
    private String fileName;
    private String fileType;

    public FilesResponseDto(UploadFiles entity) {
        this.id = entity.getId();
        this.fileOriName = entity.getFileOriName();
        this.fileName = entity.getFileName();
        this.fileType = entity.getFileType();
    }
}
