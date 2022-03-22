package cn.iocoder.springboot.lab01.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// 提供测试 API 接口

@RestController
@RequestMapping("/test")
public class TestController {
    //对于 /test/echo 接口，直接访问，无需登录。
    //对于 /test/home 接口，无法直接访问，需要进行登录。
    //对于 /test/admin 接口，需要登录「admin/admin」用户，因为需要 ADMIN 角色。
    //对于 /test/normal 接口，需要登录「normal/normal」用户，因为需要 NORMAL 角色。

    //胖友可以按照如上的说明，进行各种测试。例如说，登录「normal/normal」用户后，去访问 /test/admin 接口，会返回 403 界面，无权限~

    @GetMapping("/demo")
    public String demo() {
        return "示例返回";
    }

    @GetMapping("/home")
    public String home() {
        return "我是首页";
    }

    @GetMapping("/admin")
    public String admin() {
        return "我是管理员";
    }

    @GetMapping("/normal")
    public String normal() {
        return "我是普通用户";
    }

}
