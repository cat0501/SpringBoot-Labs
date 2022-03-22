package cn.iocoder.springboot.lab22.validation.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

//创建 @InEnum 自定义约束的注解
    //在类上，添加 @@Constraint(validatedBy = InEnumValidator.class) 注解，设置使用的自定义约束的校验器。
    //value() 属性，设置实现 IntArrayValuable 接口的类。这样，我们就能获得参数需要校验的值数组。
    //message() 属性，设置提示内容。默认为 "必须在指定范围 {value}" 。
    //其它属性，复制粘贴即可，都可以忽略不用理解。

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = InEnumValidator.class)
public @interface InEnum {

    /**
     * @return 实现 IntArrayValuable 接口的
     */
    Class<? extends IntArrayValuable> value();

    /**
     * @return 提示内容
     */
    String message() default "必须在指定范围 {value}";

    /**
     * @return 分组
     */
    Class<?>[] groups() default {};

    /**
     * @return Payload 数组
     */
    Class<? extends Payload>[] payload() default {};

    /**
     *  Defines several {@code @InEnum} constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        InEnum[] value();

    }

}
