package life.langteng.community.bean;

public enum NotificationType {

    COMMENT_QUESTION(1,"评论了您的问题:"),
    COMMENT_REPLY(2,"回复了您的评论:");


    private Integer code;

    private String message;


    NotificationType(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessageByCode(Integer code){
        NotificationType[] values = NotificationType.values();
        for (NotificationType v : values) {
            if(v.getCode() == code){
                return v.getMessage();
            }
        }
        // -------
        return null;
    }



}
