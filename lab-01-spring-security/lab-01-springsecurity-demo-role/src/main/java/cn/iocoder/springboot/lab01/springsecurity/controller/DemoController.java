package cn.iocoder.springboot.lab01.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
// 提供测试 API 接口

@RestController
@RequestMapping("/demo")
public class DemoController {
    //每个 URL 的权限验证，和「3.2.2 TestController」是一一对应的。
    //@PermitAll 注解，等价于 #permitAll() 方法，所有用户可访问。
        //重要！！！因为在「3.2.1 SecurityConfig」中，配置了 .anyRequest().authenticated() ，任何请求，访问的用户都需要经过认证。所以这里 @PermitAll 注解实际是不生效的。
        //也就是说，Java Config 配置的权限，和注解配置的权限，两者是叠加的。
    //@PreAuthorize 注解，等价于 #access(String attribute) 方法，，当 Spring EL 表达式的执行结果为 true 时，可以访问。
    //Spring Security 还有其它注解，不过不太常用，可见《区别： @Secured(), @PreAuthorize() 及 @RolesAllowed()》文章。
    //胖友可以按照如上的说明，进行各种测试。例如说，登录「normal/normal」用户后，去访问 /test/admin 接口，会返回 403 界面，无权限~

    @PermitAll
    @GetMapping("/echo")
    public String demo() {
        return "示例返回";
    }

    @GetMapping("/home")
    public String home() {
        return "我是首页";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "我是管理员";
    }

    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @GetMapping("/normal")
    public String normal() {
        return "我是普通用户";
    }

}
