package com.example.webservice.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
테스트 진행시 JUnit에 내장된 실행자 외 다른 실행자 실행
여기서는 SpringRunner 실행자 사용
스프링부터 테스트와 JUnit 사이의 연결자 역할
* */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class) // 테스트 어노테이션 중 web에 집중할 수 있는 어노테이션
public class HelloControllerTest {
    @Autowired // 스프링이 관리하는 빈을 주입 받는다.
    private MockMvc mvc; // 웹 API 테스트할 때 사용, 스프링 테스트의 시작점

    @Test
    public void hello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // mockmvc를 통해 /hello 주소로 http get 요청
                .andExpect(status().isOk()) // Mvc.perform 의 결과 검증, status 검증(200 or not)
                .andExpect(content().string(hello)); // mvc.perform 응답 본문내용 검증
    }

    @Test
    public void helloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // json 응답값 필드별로 검증할 수 있는 메소드
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
