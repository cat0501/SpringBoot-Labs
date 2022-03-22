package cn.iocoder.springboot.lab22.validation.service;

import cn.iocoder.springboot.lab22.validation.Application;
import cn.iocoder.springboot.lab22.validation.dto.UserAddDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    //<1.1> 处，注入 Validator Bean 对象。
    @Autowired
    private Validator validator;

    //执行，抛出 ConstraintViolationException 异常。日志如下：
    //javax.validation.ConstraintViolationException: get.id: 编号必须大于 0
    //	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:116)
    @Test
    public void testGet() {
        userService.get(-1);
    }

    //执行，抛出 ConstraintViolationException 异常。日志如下：
    //javax.validation.ConstraintViolationException: add.addDTO.username: 登录账号不能为空, add.addDTO.password: 密码不能为空
    //	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:116)
    //符合期望。不同于我们在调用 UserController#add(addDTO) 方法，这里被 MethodValidationInterceptor 拦截，进行参数校验，而不是 DataBinder 当中。
    @Test
    public void testAdd() {
        UserAddDTO addDTO = new UserAddDTO();
        userService.add(addDTO);
    }

    //执行，正常结束。因为进行 this.add(addDTO) 调用时，this 并不是 Spring AOP 代理对象，所以并不会被 MethodValidationInterceptor 所拦截。
    @Test
    public void testAdd01() {
        UserAddDTO addDTO = new UserAddDTO();
        userService.add01(addDTO);
    }

    //执行，抛出 IllegalStateException 异常。日志如下：
    //java.lang.IllegalStateException: Cannot find current proxy: Set 'exposeProxy' property on Advised to 'true' to make it available.
    //	at org.springframework.aop.framework.AopContext.currentProxy(AopContext.java:69)
    //理论来说，因为我们配置了 @EnableAspectJAutoProxy(exposeProxy = true) 注解，在 Spring AOP 拦截时，通过调用 AopContext.currentProxy() 方法，是可以获取到当前的代理对象。结果，此处抛出 IllegalStateException 异常。
    //显然，这里并没有将当前的代理对象，设置到 AopContext 中，所以抛出 IllegalStateException 异常。目前猜测，可能是 BUG 。😈 暂时木有心情去调试，嘿嘿。
    @Test
    public void testAdd02() {
        UserAddDTO addDTO = new UserAddDTO();
        userService.add02(addDTO);
    }


    //增加手动参数校验的示例。
    @Test
    public void testValidator() {
        //<1.2> 处，打印 validator 的类型。输出如下：
        //org.springframework.validation.beanvalidation.LocalValidatorFactoryBean@48c3205a
        // 打印，查看 validator 的类型
        System.out.println(validator);

        // 创建 UserAddDTO 对象     // <2> 处，创建 UserAddDTO 对象，即 「3.3 UserAddDTO」 ，已经添加相应的约束注解。
        UserAddDTO addDTO = new UserAddDTO();
        // 校验   // <3> 处，调用 Validator#validate(T object, Class<?>... groups) 方法，进行参数校验。
        Set<ConstraintViolation<UserAddDTO>> result = validator.validate(addDTO);
        // 打印校验结果   // <4> 处，打印校验结果。输出如下：username:登录账号不能为空  password:密码不能为空
            // 如果校验通过，则返回的 Set<ConstraintViolation<?>> 集合为空。
        for (ConstraintViolation<UserAddDTO> constraintViolation : result) {
            // 属性:消息
            System.out.println(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
        }
    }

}
