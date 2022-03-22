package cn.iocoder.springboot.lab22.validation.core.validator;

//因为对于一个枚举类来说，我们无法获得它具体有那些值。所以，我们会要求这个枚举类实现该接口，返回它拥有的所有枚举值。

/**
 * 可生成 Int 数组的接口
 */
public interface IntArrayValuable {

    /**
     * @return int 数组
     */
    int[] array();

}
