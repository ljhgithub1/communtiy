package life.langteng.community.bean;

/**
 * 评论的类型
 */
public enum CommentType {

    /**
     * 评论的是问题
     */
    QUESTION_TYPE(1),
    /**
     * 评论的是回复(评论)
     */
    COMMENT_TYPE(2);


    private Integer type;


    CommentType(Integer type){
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    /**
     * 判断type是否在枚举中存在
     * @param type
     * @return
     */
    public static boolean isExist(Integer type) {
        CommentType[] values = CommentType.values();
        for(CommentType value:values){
            if(value.type.equals(type)){
                return true;
            }
        }
        return false;
    }
}
