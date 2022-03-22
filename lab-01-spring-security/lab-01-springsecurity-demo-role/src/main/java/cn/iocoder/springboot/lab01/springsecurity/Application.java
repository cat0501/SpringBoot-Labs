package cn.iocoder.springboot.lab01.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//在「2. 快速入门」中，我们很快速的完成了 Spring Security 的入门。本小节，我们将会自定义 Spring Security 的配置，实现权限控制。
//考虑到不污染上述的示例，我们新建一个 lab-01-springsecurity-demo-role 项目。

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
