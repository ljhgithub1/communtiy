package life.langteng.community.interception;

import life.langteng.community.bean.InSession;
import life.langteng.community.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class NotificationCountInterceptor implements HandlerInterceptor {

    @Autowired
    private INotificationService notificationService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果用户都没有登录，就不用去获取通知数量信息了
        if (request.getSession().getAttribute(InSession.USER_IN_SESSION) == null) {
            return true;
        }

        Integer notificationCount = (Integer) request.getSession().getAttribute(InSession.NOTIFICATION_COUNT_IN_SESSION);

        if (notificationCount == null) {
            Integer count = notificationService.getNotificationCountByUserId();
            if(count != null){
                request.getSession().setAttribute(InSession.NOTIFICATION_COUNT_IN_SESSION,count);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
