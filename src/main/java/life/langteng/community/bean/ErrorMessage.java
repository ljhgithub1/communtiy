package life.langteng.community.bean;

public enum ErrorMessage {

    QUESTION_NOT_FOUND("你要查找的问题搬家了,换个问题试试吧!"),
    COMMENT_TYPE_NOT_FOUND("回复的类型存在"),
    COMMENT_NOT_FOUND("回复的评论已不存在"),
    COMMENT_QUESTION_NOT_FOUND("回复的问题已不存在!");

    private String message;

    private ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
