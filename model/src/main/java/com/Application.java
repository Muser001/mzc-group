package com;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(new Class[]{Application.class});
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
