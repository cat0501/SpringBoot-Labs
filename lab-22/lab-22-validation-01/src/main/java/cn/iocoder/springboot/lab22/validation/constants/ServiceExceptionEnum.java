package cn.iocoder.springboot.lab22.validation.constants;

//我们先把 《芋道 Spring Boot SpringMVC 入门》 的 「5. 全局异常处理」 小节中，需要用到的类，全部复制过来。
//在 cn.iocoder.springboot.lab22.validation.constants 包路径下，复制 ServiceExceptionEnum 类。
//在 cn.iocoder.springboot.lab22.validation.core.exception 包路径下，复制 ServiceException 类。
//在 cn.iocoder.springboot.lab22.validation.core.vo 包路径下，复制 CommonResult 类。
//在 cn.iocoder.springboot.lab22.validation.core.web 包路径下，复制 GlobalExceptionHandler 和 GlobalResponseBodyHandler 类。

/**
 * 业务异常枚举
 */
public enum ServiceExceptionEnum {

    // ========== 系统级别 ==========
    SUCCESS(0, "成功"),
    SYS_ERROR(2001001000, "服务端发生异常"),
    MISSING_REQUEST_PARAM_ERROR(2001001001, "参数缺失"),
    //修改 ServiceExceptionEnum 枚举类，增加校验参数不通过的错误码枚举。
    INVALID_REQUEST_PARAM_ERROR(2001001002, "请求参数不合法"),

    // ========== 用户模块 ==========
    USER_NOT_FOUND(1001002000, "用户不存在"),

    // ========== 订单模块 ==========

    // ========== 商品模块 ==========
    ;

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误提示
     */
    private final String message;

    ServiceExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
