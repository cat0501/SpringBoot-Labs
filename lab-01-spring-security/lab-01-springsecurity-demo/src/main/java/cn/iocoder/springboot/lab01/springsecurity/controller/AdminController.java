package cn.iocoder.springboot.lab01.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // 这里，我们先提供一个 "/admin/demo" 接口，用于测试未登录时，会被拦截到登录界面。
    //项目启动成功后，浏览器访问 http://127.0.0.1:9001/admin/demo 接口。因为未登录，所以被 Spring Security 拦截到登录界面。如下图所示：

    //因为我们没有自定义登录界面，所以默认会使用 DefaultLoginPageGeneratingFilter 类，生成上述界面。
    //输入我们在「2.3 配置文件」中配置的「user/user」账号，进行登录。登录完成后，因为 Spring Security 会记录被拦截的访问地址，所以浏览器自动动跳转 http://127.0.0.1:9001/admin/demo 接口。访问结果如下图所示：
    @GetMapping("/demo")
    public String demo() {
        return "示例返回";
    }

}
