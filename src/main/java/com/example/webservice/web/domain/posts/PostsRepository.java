package com.example.webservice.web.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
* Jpa Repository = Mybatis 등에서 DAO 라 불리는 DB Layer
* 인터페이스로 생성 후
* JpaRepository<Entitiy 클래스, PK 타입> 상속하면 기본적인 crud 메소드 자동 생성
* */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("select p from Posts p order by p.id desc ")
    List<Posts> findAllDesc();

    List<Posts> findByTitleContaining(String keyword); // 제목
    List<Posts> findByContentContaining(String keyword); // 내용

    // 제목 + 내용 검색
    @Query("select p from Posts p where p.content like %:keyword% or p.title like %:keyword%")
    List<Posts> findAllContaining(@Param("keyword") String keyword);
}
