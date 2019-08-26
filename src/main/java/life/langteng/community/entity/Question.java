package life.langteng.community.entity;


import lombok.Data;

@Data
public class Question {
    /**
     * id
     */
    private Integer id;
    /**
     * 问题题目
     */
    private String title;
    /**
     * 问题描述
     */
    private String description;
    /**
     * 问题标签
     */
    private String tag;
    /**
     * 问题提出者的id
     */
    private Integer creator;
    /**
     * 创建的时间戳
     */
    private Long gmtCreate;

    /**
     * 修改的时间戳
     */
    private Long gmtModified;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 查看数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tag='" + tag + '\'' +
                ", creator=" + creator +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", commentCount=" + commentCount +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                '}';
    }
}
