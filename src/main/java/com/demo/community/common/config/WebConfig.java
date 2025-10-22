package com.demo.community.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

// [삭제 예정 파일]
// 임시 사진 업로드 구현을 위해 만든 코드
// `uploads/` 디렉토리를 백엔드 서버 도메인으로 공개 접근 가능하게 함.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String uploadDir = "./uploads";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + Paths.get(uploadDir).toAbsolutePath() + "/");
    }
}
