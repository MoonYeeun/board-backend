package com.example.webservice.web.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
* 모든 entitiy 들의 상위클래스가 되어 entity들의 createdDate, modifiedDate 자동 관리
* */
@Getter
@MappedSuperclass // BaseTimeEntity 상속할 경우 필드들도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 포함
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
