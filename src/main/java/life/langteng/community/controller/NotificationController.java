package life.langteng.community.controller;

import life.langteng.community.dto.NotificationDTO;
import life.langteng.community.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    /**
     * 这里差一个分页
     * @param request
     * @return
     */
    @RequestMapping("/profile/replies")
    public String replies(HttpServletRequest request
                          ){
        List<NotificationDTO> notificationDTOS = notificationService.queryNotificationsByCurrentUserId();

        int count = notificationService.getNotificationCountByUserId();
        request.getSession().setAttribute("notificationCount",count);

        request.setAttribute("notifications",notificationDTOS);
        return "notification";
    }
}
