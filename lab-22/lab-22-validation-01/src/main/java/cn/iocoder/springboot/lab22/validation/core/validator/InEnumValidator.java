package cn.iocoder.springboot.lab22.validation.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

//自定义约束的校验器
//实现 ConstraintValidator 接口。
    //第一个泛型为 A extends Annotation ，设置对应的自定义约束的注解。例如说，这里我们设置了 @InEnum 注解。
    //第二个泛型为 T ，设置对应的参数值的类型。例如说，这里我们设置了 Integer 类型。
//实现 #initialize(annotation) 方法，获得 @InEnum 注解的 values() 属性，获得值数组，设置到 values 属性种。
//实现 #isValid(value, context) 方法，实现校验参数值，是否在 values 范围内。
    //<2.1> 处，校验参数值在范围内，直接返回 true ，校验通过。
    //<2.2.1> 处，校验不通过，自定义提示语句。
    //<2.2.2> 处，校验不通过，所以返回 false 。

//至此，我们已经完成了自定义约束的实现。下面，我们来进行下测试。
public class InEnumValidator implements ConstraintValidator<InEnum, Integer> {

    /**
     * 值数组
     */
    private Set<Integer> values;

    @Override
    public void initialize(InEnum annotation) {
        IntArrayValuable[] values = annotation.value().getEnumConstants();
        if (values.length == 0) {
            this.values = Collections.emptySet();
        } else {
            this.values = Arrays.stream(values[0].array()).boxed().collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //<2.1> 处，校验参数值在范围内，直接返回 true ，校验通过。
        // 校验通过
        if (values.contains(value)) {
            return true;
        }
        //<2.2.1> 处，校验不通过，自定义提示语句。
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())).addConstraintViolation(); // 重新添加错误提示语句
        return false;//<2.2.2> 处，校验不通过，所以返回 false 。
    }

}
