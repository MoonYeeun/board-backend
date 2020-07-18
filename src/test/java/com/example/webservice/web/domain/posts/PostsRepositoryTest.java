package com.example.webservice.web.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest // h2 데이터베이스 자동 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // JUnit 에서 단위 테스트가 끝날 때마다 수행되는 메소드 지정
    // 여러 테스트가 동시에 수행될 경우 테스트용 데이터베이스인 H2에 데이터 그대로 남아
    // 다음 테스트 실행 시 테스트 실패할 수 있다.
    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void posts_조회() {
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // 테이블 posts에 insert/update 쿼리 실행 (존재하면 update 없으면 insert)
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("aams82@hanmail.net")
                .build());

        // 테이블 posts에 있는 모든 데이터 조회
        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        LocalDateTime now = LocalDateTime.of(2020, 7, 16, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("aams82@hanmail.net")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println("--------create date=" + posts.getCreateDate() +
                ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
