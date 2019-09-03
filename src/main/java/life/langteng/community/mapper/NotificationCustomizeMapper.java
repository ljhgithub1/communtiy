package life.langteng.community.mapper;

import life.langteng.community.dto.NotificationDTO;

import java.util.List;

public interface NotificationCustomizeMapper {

    List<NotificationDTO> queryNotificationsByUserId(int userId);
}
