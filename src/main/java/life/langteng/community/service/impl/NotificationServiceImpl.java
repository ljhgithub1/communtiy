package life.langteng.community.service.impl;

import life.langteng.community.bean.NotificationType;
import life.langteng.community.dto.NotificationDTO;
import life.langteng.community.entity.Notification;
import life.langteng.community.entity.NotificationExample;
import life.langteng.community.mapper.NotificationCustomizeMapper;
import life.langteng.community.mapper.NotificationMapper;
import life.langteng.community.service.INotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    private NotificationCustomizeMapper notificationCustomizeMapper;

    @Override
    public int getNotificationCountByUserId(int userId) {
        return 0;
    }

    @Override
    public List<NotificationDTO> queryNotificationsByUserId(int userId) {
        List<NotificationDTO> notificationDTOS = notificationCustomizeMapper.queryNotificationsByUserId(userId);

        for (NotificationDTO notificationDTO: notificationDTOS) {
            String messageByCode = NotificationType.getMessageByCode(notificationDTO.getType());
            notificationDTO.setTypeHint(messageByCode);
        }

        return notificationDTOS;
    }


}
