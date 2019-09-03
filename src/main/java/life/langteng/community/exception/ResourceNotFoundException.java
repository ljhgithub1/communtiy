package life.langteng.community.exception;

import life.langteng.community.bean.ErrorMessage;

/**
 * 所有资源没有找到异常的父类
 *
 */
public abstract class ResourceNotFoundException extends RuntimeException {

    private Integer code;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
