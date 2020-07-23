package com.example.webservice.service.posts;

import com.example.webservice.web.domain.files.FilesRepository;
import com.example.webservice.web.domain.posts.Posts;
import com.example.webservice.web.domain.posts.PostsRepository;
import com.example.webservice.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // 생성자로 빈 주입
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final FilesRepository filesRepository;

    // 게시글 등록
    @Transactional
    public Long save(PostsSaveRequestsDto requestsDto) {
        return postsRepository.save(requestsDto.toEntity()).getId();
    }

    // 게시글 전체 불러오기
    @Transactional
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
    // 특정 게시글 불러오기
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    // 게시글 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    // 게시글 조회
    public List<PostsListResponseDto> search(String keyword, String type) {
        // 제목 검색
        if(type.equals("title")) {
            return postsRepository.findByTitleContaining(keyword).stream()
                    .map(PostsListResponseDto::new)
                    .collect(Collectors.toList());
        }
        // 내용 검색
        else if(type.equals("content")) {
            return postsRepository.findByContentContaining(keyword).stream()
                    .map(PostsListResponseDto::new)
                    .collect(Collectors.toList());
        }
        // 제목 + 내용 검색
        else {
            return postsRepository.findAllContaining(keyword).stream()
                    .map(PostsListResponseDto::new)
                    .collect(Collectors.toList());
        }
    }
}
