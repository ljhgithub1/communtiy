package life.langteng.community.exception;


import life.langteng.community.bean.ReminderMessage;

/**
 *  程序异常
 */
public class CommunityException extends RuntimeException{
    /**
     * 状态码
     */
    private Integer code;


    public CommunityException() {
    }

    public CommunityException(ReminderMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
