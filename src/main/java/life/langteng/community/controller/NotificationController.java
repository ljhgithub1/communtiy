package life.langteng.community.controller;

import life.langteng.community.entity.Notification;
import life.langteng.community.entity.User;
import life.langteng.community.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @RequestMapping("/profile/replies")
    public String replies(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        List<Notification> notifications = notificationService.queryNotificationsByUserId(user.getId());

        request.setAttribute("notifications",notifications);
        return "notification";
    }
}
