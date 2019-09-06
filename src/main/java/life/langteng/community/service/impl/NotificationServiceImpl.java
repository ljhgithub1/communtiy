package life.langteng.community.service.impl;

import life.langteng.community.bean.NotificationType;
import life.langteng.community.dto.NotificationDTO;
import life.langteng.community.entity.Notification;
import life.langteng.community.entity.User;
import life.langteng.community.mapper.NotificationCustomizeMapper;
import life.langteng.community.mapper.NotificationMapper;
import life.langteng.community.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {


    @Autowired
    private NotificationCustomizeMapper notificationCustomizeMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public Integer getNotificationCountByUserId() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        User user = (User)sra.getRequest().getSession().getAttribute("user");
        if(user == null){
            throw new RuntimeException("请先登录!");
        }
        int count = notificationCustomizeMapper.getNotificationCountByUserId(user.getId());
        return count;
    }

    @Override
    public List<NotificationDTO> queryNotificationsByCurrentUserId() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        User user = (User)sra.getRequest().getSession().getAttribute("user");


        if(user == null){
            throw new RuntimeException("请先登录!");
        }

        List<NotificationDTO> notificationDTOS = notificationCustomizeMapper.queryNotificationsByUserId(user.getId());

        for (NotificationDTO notificationDTO: notificationDTOS) {
            String messageByCode = NotificationType.getMessageByCode(notificationDTO.getType());
            notificationDTO.setTypeHint(messageByCode);
        }

        return notificationDTOS;
    }

    @Override
    public void editNotificationStatus(Integer notificationId) {
        if (notificationId == null) {
            return;
        }
        Notification no = new Notification();
        no.setId(notificationId);
        no.setStatus(1);
        notificationMapper.updateByPrimaryKeySelective(no);
    }


}
