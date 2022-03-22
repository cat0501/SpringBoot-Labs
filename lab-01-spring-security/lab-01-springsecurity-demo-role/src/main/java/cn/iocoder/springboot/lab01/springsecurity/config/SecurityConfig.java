package cn.iocoder.springboot.lab01.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// 创建 SecurityConfig 配置类，继承 WebSecurityConfigurerAdapter 抽象类，实现 Spring Security 在 Web 场景下的自定义配置。
// 我们可以通过重写 WebSecurityConfigurerAdapter 的方法，实现自定义的 Spring Security 的配置。

@Configuration
// 修改 SecurityConfig 配置类，增加 @EnableGlobalMethodSecurity 注解，开启对 Spring Security 注解的方法，进行权限验证。代码如下：
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //然后，我们重写 #configure(HttpSecurity http) 方法，主要配置 URL 的权限控制。代码如下：

    //<X> 处，调用 HttpSecurity#authorizeRequests() 方法，开始配置 URL 的权限控制。注意看艿艿配置的四个权限控制的配置。下面，是配置权限控制会使用到的方法：
        //antMatchers(String... antPatterns)方法，配置匹配的 URL 地址，基于 Ant 风格路径表达式 ，可传入多个。
        //【常用】#permitAll() 方法，所有用户可访问。
        //【常用】#hasRole(String role) 方法， 拥有指定角色的用户可访问。
        //【最牛】#access(String attribute) 方法，当 Spring EL 表达式的执行结果为 true 时，可以访问。
    //<Y> 处，调用 HttpSecurity#formLogin() 方法，设置 Form 表单登录。
        //如果胖友想要自定义登录页面，可以通过 #loginPage(String loginPage) 方法，来进行设置。不过这里我们希望像「2. 快速入门」一样，使用默认的登录界面，所以不进行设置。
    //<Z> 处，调用 HttpSecurity#logout() 方法，配置退出相关。
        //如果胖友想要自定义退出页面，可以通过 #logoutUrl(String logoutUrl) 方法，来进行设置。不过这里我们希望像「2. 快速入门」一样，使用默认的退出界面，所以不进行设置。
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // <X> 配置请求地址的权限
                .authorizeRequests()
                    .antMatchers("/test/demo").permitAll() // 所有用户可访问
                    .antMatchers("/test/admin").hasRole("ADMIN") // 需要 ADMIN 角色
                    .antMatchers("/test/normal").access("hasRole('ROLE_NORMAL')") // 需要 NORMAL 角色。
                    // 任何请求，访问的用户都需要经过认证
                    .anyRequest().authenticated()
                .and()
                // <Y> 设置 Form 表单登陆
                .formLogin()
//                    .loginPage("/login") // 登陆 URL 地址
                    .permitAll() // 所有用户可访问
                .and()
                // <Z> 配置退出相关
                .logout()
//                    .logoutUrl("/logout") // 退出 URL 地址
                    .permitAll(); // 所有用户可访问
    }


    //首先，我们重写 #configure(AuthenticationManagerBuilder auth) 方法，实现 AuthenticationManager 认证管理器。代码如下：

    //<X> 处，调用 AuthenticationManagerBuilder#inMemoryAuthentication() 方法，使用内存级别的 InMemoryUserDetailsManager Bean 对象，提供认证的用户信息。
            //Spring 内置了两种 UserDetailsManager 实现：
                    //InMemoryUserDetailsManager，和「2. 快速入门」是一样的。
                    //JdbcUserDetailsManager ，基于 JDBC的 JdbcUserDetailsManager 。
            //实际项目中，我们更多采用调用 AuthenticationManagerBuilder#userDetailsService(userDetailsService) 方法，使用自定义实现的 UserDetailsService 实现类，更加灵活且自由的实现认证的用户信息的读取。
    //<Y> 处，调用 AbstractDaoAuthenticationConfigurer#passwordEncoder(passwordEncoder) 方法，设置 PasswordEncoder 密码编码器。
            //在这里，为了方便，我们使用 NoOpPasswordEncoder 。实际上，等于不使用 PasswordEncoder ，不配置的话会报错。
            //生产环境下，推荐使用 BCryptPasswordEncoder 。更多关于 PasswordEncoder 的内容，推荐阅读《该如何设计你的 PasswordEncoder?》文章。
    //<Z> 处，配置了「admin/admin」和「normal/normal」两个用户，分别对应 ADMIN 和 NORMAL 角色。相比「2. 快速入门」来说，可以配置更多的用户。

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                // <X> 使用内存中的 InMemoryUserDetailsManager
                inMemoryAuthentication()
                // <Y> 不使用 PasswordEncoder 密码编码器
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                // <Z> 配置 admin 用户
                .withUser("admin").password("admin").roles("ADMIN")
                // <Z> 配置 normal 用户
                .and().withUser("normal").password("normal").roles("NORMAL");
    }

}
