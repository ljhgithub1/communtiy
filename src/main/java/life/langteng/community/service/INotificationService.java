package life.langteng.community.service;

import life.langteng.community.dto.NotificationDTO;

import java.util.List;

public interface INotificationService {

    int getNotificationCountByUserId(int userId);

    List<NotificationDTO> queryNotificationsByUserId(int userId);
}
