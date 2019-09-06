package life.langteng.community.bean;

/**
 * 提示信息
 */
public enum ReminderMessage {

    SUCCESS(2000,"请求成功!"),
    QUESTION_NOT_FIND(2001,"你要查找的问题搬家了,换个问题试试吧!"),
    COMMENT_TYPE_NOT_FIND(2002,"回复的类型存在"),
    COMMENT_NOT_FIND(2003,"回复的评论已不存在"),
    COMMENT_QUESTION_NOT_FIND(2004,"回复的问题已不存在!"),
    PARAM_VALID_FAIL(2005,"参数验证失败!"),
    USER_INFO_NOT_EXISTS(3000,"github获取的用户信息不存在"),
    USER_NOT_LOGIN(3001,"该操作需要登陆，请先登录!"),
    SHOULD_FIND_ONE_BUT_FIND_MANY(4000,"Account查找到多个不同的用户");

    private String message;

    private Integer code;

    ReminderMessage(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
