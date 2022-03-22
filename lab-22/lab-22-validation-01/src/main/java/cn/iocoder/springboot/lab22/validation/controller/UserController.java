package cn.iocoder.springboot.lab22.validation.controller;

import cn.iocoder.springboot.lab22.validation.dto.UserAddDTO;
import cn.iocoder.springboot.lab22.validation.dto.UserUpdateDTO;
import cn.iocoder.springboot.lab22.validation.dto.UserUpdateGenderDTO;
import cn.iocoder.springboot.lab22.validation.dto.UserUpdateStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/users")
//在类上，添加 @Validated 注解，表示 UserController 是所有接口都需要进行参数校验。
@Validated
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/get")
    //对于 #get(id) 方法，我们在 id 参数上，添加了 @Min 注解，校验 id 必须大于 0 。
    public void get(@RequestParam("id") @Min(value = 1L, message = "编号必须大于 0") Integer id) {
        logger.info("[get][id: {}]", id);
    }

    //对于 #add(addDTO) 方法，我们在 addDTO 参数上，添加了 @Valid 注解，实现对该参数的校验。
    @PostMapping("/add")
    public void add(@Valid UserAddDTO addDTO) {
        logger.info("[add][addDTO: {}]", addDTO);
    }


    //----示例我们是已经成功跑通了，但是呢，这里有几点差异性，我们要来理解下。

    // 第一点，#get(id) 方法上，我们并没有给 id 添加 @Valid 注解，而 #add(addDTO) 方法上，我们给 addDTO 添加 @Valid 注解。这个差异，是为什么呢？
    //因为 UserController 使用了 @Validated 注解，那么 Spring Validation 就会使用 AOP 进行切面，进行参数校验。而该切面的拦截器，使用的是 MethodValidationInterceptor 。
        //对于 #get(id) 方法，需要校验的参数 id ，是平铺开的，所以无需添加 @Valid 注解。
        //对于 #add(addDTO) 方法，需要校验的参数 addDTO ，实际相当于嵌套校验，要校验的参数的

    // 第二点，#get(id) 方法的返回的结果是 status = 500 ，而 #add(addDTO) 方法的返回的结果是 status = 400 。
        //对于 #get(id) 方法，在 MethodValidationInterceptor 拦截器中，校验到参数不正确，会抛出 ConstraintViolationException 异常。
        //对于 #add(addDTO) 方法，因为 addDTO 是个 POJO 对象，所以会走 SpringMVC 的 DataBinder 机制，它会调用 DataBinder#validate(Object... validationHints) 方法，进行校验。在校验不通过时，会抛出 BindException 。
    //在 SpringMVC 中，默认使用 DefaultHandlerExceptionResolver 处理异常。
    //对于 BindException 异常，处理成 400 的状态码。
    //对于 ConstraintViolationException 异常，没有特殊处理，所以处理成 500 的状态码。

    // 第三点，无论是 #get(id) 方法，还是 #add(addDTO) 方法，它们的返回提示都非常不友好，那么该怎么办呢？
    //参考 《芋道 Spring Boot SpringMVC 入门》 的 「5. 全局异常处理」 ，使用 @ExceptionHandler 注解，实现自定义的异常处理。
    //这个，我们在本文的 4. 处理校验异常 小节中，来提供具体示例。



    //增加修改性别 API 接口
    @PostMapping("/update_gender")
    public void updateGender(@Valid UserUpdateGenderDTO updateGenderDTO) {
        logger.info("[updateGender][updateGenderDTO: {}]", updateGenderDTO);
    }

    @PostMapping("/update_status_true")
    public void updateStatusTrue(@Validated(UserUpdateStatusDTO.Group01.class) UserUpdateStatusDTO updateStatusDTO) {
        logger.info("[updateStatusTrue][updateStatusDTO: {}]", updateStatusDTO);
    }

    @PostMapping("/update_status_false")
    public void updateStatusFalse(@Validated(UserUpdateStatusDTO.Group02.class) UserUpdateStatusDTO updateStatusDTO) {
        logger.info("[updateStatusFalse][updateStatusDTO: {}]", updateStatusDTO);
    }

    @PostMapping("/update")
    public void update(@Valid UserUpdateDTO updateDTO) {
        logger.info("[update][updateDTO: {}]", updateDTO);
    }

}
