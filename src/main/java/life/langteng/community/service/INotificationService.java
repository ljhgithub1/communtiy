package life.langteng.community.service;

import life.langteng.community.dto.NotificationDTO;

import java.util.List;

public interface INotificationService {

    Integer getNotificationCountByUserId();

    List<NotificationDTO> queryNotificationsByCurrentUserId();

    void editNotificationStatus(Integer notificationId);
}
