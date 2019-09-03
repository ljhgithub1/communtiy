package life.langteng.community.dto;

import life.langteng.community.entity.User;
import lombok.Data;

@Data
public class CommentDTO {

    private Integer id;

    private Integer parentId;

    private Integer type;

    private String content;

    private Integer commenter;

    private Integer likeCount;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer commentCount;

    private User user;
}
