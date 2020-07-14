package com.example.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 프로젝트의 메인 클래스
// 'SpringBootApplication' 이 있는 위치로 부터 설정 읽어가기 때문에 항상 프로젝트 최상단에 위치!
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // springapplication.run 으로 인해 내장 WAS 실행
        // 따로 톰캣 설치 필요없이 스프링부트로 만들어진 jar 파일로 실행가능
        // 내장 WAS 사용 권장이유 : 언제 어디서나 같은 환경에서 스프링부터 배포 가능
        SpringApplication.run(Application.class, args);
    }
}
