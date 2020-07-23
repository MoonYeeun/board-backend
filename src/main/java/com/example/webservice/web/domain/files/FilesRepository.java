package com.example.webservice.web.domain.files;

import com.example.webservice.web.dto.FilesResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilesRepository extends JpaRepository<UploadFiles, Long> {
    @Query("select f from UploadFiles f where boardId = :boardId")
    List<UploadFiles> findByBoardId(@Param("boardId") Long id);
}
