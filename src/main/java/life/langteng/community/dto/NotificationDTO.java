package life.langteng.community.dto;

import life.langteng.community.entity.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;

    private Integer type;

    private String typeHint;

    private Long gmtCreate;

    private Integer status;

    private User replyer;

    private String receiverName;

    private String outerTitle;

}
