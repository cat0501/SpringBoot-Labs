package cn.iocoder.springboot.lab22.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
// 芋道 Spring Boot 参数校验 Validation 入门
// https://www.iocoder.cn/Spring-Boot/Validation/?yudao

//希望阅读完本文，能够让胖友更加舒适且优雅的完成各种需要参数校验的地方。😈 不说了，
// 艿艿赶紧给自己的系统去把参数校验给补全，嘿嘿。

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true) // http://www.voidcn.com/article/p-zddcuyii-bpt.html
// 重点是配置 exposeProxy = true ，因为我们希望 Spring AOP 能将当前代理对象设置到 AopContext 中。
// 具体用途，我们会在下文看到。想要提前看的胖友，可以看看 《Spring AOP 通过获取代理对象实现事务切换》 文章。
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
