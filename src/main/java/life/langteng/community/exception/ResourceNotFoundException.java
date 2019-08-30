package life.langteng.community.exception;

import life.langteng.community.bean.ErrorMessage;

/**
 * 所有资源没有找到异常的父类
 *
 */
public abstract class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

}
