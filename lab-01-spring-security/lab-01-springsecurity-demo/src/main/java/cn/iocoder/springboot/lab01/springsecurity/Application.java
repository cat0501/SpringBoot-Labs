package cn.iocoder.springboot.lab01.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 在本小节中，我们来快速入门下 Spring Security ，实现访问 API 接口时，需要首先进行登录，才能进行访问。

// 依赖：spring-boot-starter-security



@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
