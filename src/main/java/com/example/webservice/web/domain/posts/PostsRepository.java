package com.example.webservice.web.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/*
* Jpa Repository = Mybatis 등에서 DAO 라 불리는 DB Layer
* 인터페이스로 생성 후
* JpaRepository<Entitiy 클래스, PK 타입> 상속하면 기본적인 crud 메소드 자동 생성
* */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
