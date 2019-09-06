package life.langteng.community.mapper;

import life.langteng.community.dto.NotificationDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationCustomizeMapper {

    List<NotificationDTO> queryNotificationsByUserId(@Param("userId") int userId);

    int getNotificationCountByUserId(@Param("userId") Integer userId);

    List<NotificationDTO> queryNotificationsByPage(@Param("userId") Integer userId, @Param("position") Integer position,@Param("pageSize") Integer pageSize);
}
