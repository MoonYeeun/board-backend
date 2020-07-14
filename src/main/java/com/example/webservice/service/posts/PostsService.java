package com.example.webservice.service.posts;

import com.example.webservice.web.domain.posts.PostsRepository;
import com.example.webservice.web.dto.PostsSaveRequestsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor // 생성자로 빈 주입
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public String save(PostsSaveRequestsDto requestsDto) {
        postsRepository.save(requestsDto.toEntity());
        return "성공적으로 저장되었습니다.";
    }

    // 리스트 목록 불러오기
    public List getPostsList() {
        postsRepository.findAll().forEach(post -> {
            System.out.println(post.getTitle());
        });
        return (List) postsRepository.findAll();
    }
}
